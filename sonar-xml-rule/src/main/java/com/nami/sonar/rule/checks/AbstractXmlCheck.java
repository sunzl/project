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
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.WildcardPattern;

/**
 * Abstract superclass for checks.
 *
 * @author Matthijs Galesloot
 */
public abstract class AbstractXmlCheck {

  private RuleKey ruleKey;
  private XmlSourceCode xmlSourceCode;

  protected final void createViolation(Integer linePosition, String message) {
    getWebSourceCode().addViolation(new XmlIssue(ruleKey, linePosition, message));
  }

  protected XmlSourceCode getWebSourceCode() {
    return xmlSourceCode;
  }

  /**
   * Check with ant style filepattern if the file is included.
   */
  protected boolean isFileIncluded(@Nullable String filePattern) {
    if (filePattern != null) {
      return WildcardPattern.create(filePattern)
        .match(getWebSourceCode().getLogicalPath());

    } else {
      return true;
    }
  }

  public final void setRuleKey(RuleKey ruleKey) {
    this.ruleKey = ruleKey;
  }

  public RuleKey getRuleKey() {
    return ruleKey;
  }

  protected void setWebSourceCode(XmlSourceCode xmlSourceCode) {
    this.xmlSourceCode = xmlSourceCode;
  }

  public abstract void validate(XmlSourceCode xmlSourceCode);
}
