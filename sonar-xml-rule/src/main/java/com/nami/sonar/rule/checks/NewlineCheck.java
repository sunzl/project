/*
 * SonarQube XML Plugin
 * Copyright (C) 2010-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.nami.sonar.rule.checks;

import javax.annotation.Nullable;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * RSPEC-2321.
 * Perform check for newline after elements.
 * @author Matthijs Galesloot
 */
@Rule(key = "NewlineCheck")
public class NewlineCheck extends AbstractXmlCheck {

  /**
   * Validate newlines for node.
   */
  private void validateNewline(Node node) {

    // check if we have a newline after the elements and after each childelement.
    boolean newline = false;
    Node lastChild = null;

    for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
      short nodeType = child.getNodeType();

      if (nodeType == Node.COMMENT_NODE) {
        lastChild = child;

      } else if (nodeType == Node.ELEMENT_NODE) {
        // check if there is a new node before we have had any newlines.
        if (!newline) {
          createViolation(getWebSourceCode().getLineForNode(child), "Start every element on a separate line.");
        } else {
          newline = false;
        }
        lastChild = child;

      } else if (nodeType == Node.TEXT_NODE) {
        // newline check is OK if there is non whitespace or the whitespace contains a newline
        String textContent = child.getTextContent();
        if (!StringUtils.isWhitespace(textContent) || textContent.contains("\n")) {
          newline = true;
        }
      }
    }

    // validate first last child.
    validateLastChild(newline, lastChild);

    checkChildElements(node);
  }

  private void checkChildElements(Node node) {
    for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        validateNewline(child);
      }
    }
  }

  private void validateLastChild(boolean newlineAfterLastChild, @Nullable Node lastChild) {
    if (!newlineAfterLastChild && lastChild != null) {
      createViolation(getWebSourceCode().getLineForNode(lastChild), "Missing newline after last element");
    }
  }

  @Override
  public void validate(XmlSourceCode xmlSourceCode) {
    setWebSourceCode(xmlSourceCode);

    Document document = getWebSourceCode().getDocument(false);
    if (document.getDocumentElement() != null) {
      validateNewline(document.getDocumentElement());
    }
  }
}
