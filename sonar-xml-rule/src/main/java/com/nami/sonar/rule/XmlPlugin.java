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
package com.nami.sonar.rule;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import com.nami.sonar.rule.language.Xml;
import com.nami.sonar.rule.rules.XmlRulesDefinition;
import com.nami.sonar.rule.rules.XmlSonarWayProfile;

/**
 * XML Plugin publishes extensions to sonar engine.
 *
 * @author Matthijs Galesloot
 */
public final class XmlPlugin implements Plugin {

  public static final String FILE_SUFFIXES_KEY = "sonar.xml.file.suffixes";
  
  public static final String REPOSITORY_KEY = "customPluginForXml";

  public void define(Context context) {
    context.addExtensions(
      PropertyDefinition.builder(XmlPlugin.REPOSITORY_KEY)
        .name("analyze xml file")
        .description("Analysis mybatis mapping file,check whether sql compliance")
        .defaultValue(".xml")
        .category("XML")
        .onQualifiers(Qualifiers.PROJECT)
        .build(),
      Xml.class,
      XmlRulesDefinition.class,
      XmlSonarWayProfile.class,
      XmlSensor.class);
  }

}
