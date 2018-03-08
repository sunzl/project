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
package com.nami.sonar.rule.highlighting;

import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class HighlightingData {

  private Integer startOffset;
  private Integer endOffset;
  private TypeOfText typeOfText;

  HighlightingData(Integer startOffset, Integer endOffset, TypeOfText typeOfText) {
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.typeOfText = typeOfText;
  }

  public Integer startOffset() {
    return startOffset;
  }

  public Integer endOffset() {
    return endOffset;
  }

  public TypeOfText highlightCode() {
    return typeOfText;
  }

}
