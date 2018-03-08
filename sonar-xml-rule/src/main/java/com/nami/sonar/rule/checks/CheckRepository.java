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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckRepository {

  public static final String REPOSITORY_KEY = "xml";
  public static final String REPOSITORY_NAME = "SonarAnalySql";
  public static final String SONAR_WAY_PROFILE_NAME = "Sonar way";

  private CheckRepository() {
  }

  public static List<AbstractXmlCheck> getChecks() {
    return Arrays.asList(
      new SqlCheckRule());
    /*return Arrays.asList(
    	      new IllegalTabCheck(),
    	      new IndentCheck(),
    	      new NewlineCheck(),
    	      new XmlSchemaCheck(),
    	      new CharBeforePrologCheck(),
    	      new ParsingErrorCheck(),
    	      new XPathCheck());*/
  }

  public static List<Class> getCheckClasses() {
    return getChecks().stream().map(AbstractXmlCheck::getClass).collect(Collectors.toList());
  }

}
