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

import org.apache.commons.lang.StringUtils;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * RSPEC-1120.
 * Perform check for indenting of elements.
 * @author Matthijs Galesloot
 */
@Rule(key = "IndentCheck")
public class IndentCheck extends AbstractXmlCheck {

  private static final String MESSAGE = "Make this line start at column %s.";

  @RuleProperty(
    key = "indentSize",
    description = "Number of white-spaces of an indent. If this property is not set, we just check that the code is indented.",
    defaultValue = "2",
    type = "INTEGER")
  private int indentSize = 2;

  @RuleProperty(
    key = "tabSize",
    description = "Equivalent number of spaces of a tabulation",
    defaultValue = "2",
    type = "INTEGER")
  private int tabSize = 2;

  /**
   * Collect the indenting whitespace before this node.
   */
  private int collectIndent(Node node) {
    int indent = 0;
    for (Node sibling = node.getPreviousSibling(); sibling != null; sibling = sibling.getPreviousSibling()) {
      short nodeType = sibling.getNodeType();

      if (nodeType == Node.COMMENT_NODE || nodeType == Node.ELEMENT_NODE) {
        return indent;

      } else if (nodeType == Node.TEXT_NODE) {
        String text = sibling.getTextContent();
        if (!StringUtils.isWhitespace(text)) {
          // non whitespace found, we are done
          return indent;
        }
        for (int i = text.length() - 1; i >= 0; i--) {
          char c = text.charAt(i);
          switch (c) {
            case '\n':
              // newline found, we are done
              return indent;
            case '\t':
              // add tabsize
              indent += tabSize;
              break;
            case ' ':
              // add one space
              indent++;
              break;
            default:
              break;
          }
        }
      }
    }
    return indent;
  }

  /**
   * Get the depth of this node in the node hierarchy.
   */
  private static int getDepth(Node node) {
    int depth = 0;
    for (Node parent = node.getParentNode(); parent.getParentNode() != null; parent = parent.getParentNode()) {
      depth++;
    }
    return depth;
  }

  @Override
  public void validate(XmlSourceCode xmlSourceCode) {
    setWebSourceCode(xmlSourceCode);

    Document document = getWebSourceCode().getDocument(false);
    if (document.getDocumentElement() != null) {
      validateIndent(document.getDocumentElement());
    }
  }

  /**
   * Validate the indent for this node.
   */
  private boolean validateIndent(Node node) {

    int depth = getDepth(node);
    int indent = collectIndent(node);

    int expectedIndent = depth * indentSize;

    if (expectedIndent != indent) {
      createViolation(getWebSourceCode().getLineForNode(node), String.format(MESSAGE, expectedIndent + 1));
      return true;
    }

    // check the child elements

    boolean issueOnLine = false;

    for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
      switch (child.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (!issueOnLine) {
            issueOnLine = validateIndent(child);
          }
          break;
        case Node.TEXT_NODE:
          if (child.getTextContent().contains("\n")) {
            issueOnLine = false;
          }
          break;
        case Node.COMMENT_NODE:
        default:
          break;
      }
    }

    return false;
  }
}
