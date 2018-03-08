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
package com.nami.sonar.rule.compat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;

/**
 * A compatibility wrapper for InputFile. See class hierarchy.
 *
 * All methods of this class simply delegate to the wrapped instance, except `wrapped`.
 */
public class CompatibleInputFile {

  private final InputFile wrapped;

  public CompatibleInputFile(InputFile wrapped) {
    this.wrapped = wrapped;
  }

  /**
   * Get the original InputFile instance wrapped inside.
   *
   * @return original InputFile instance
   */
  public InputFile wrapped() {
    return wrapped;
  }

  public String absolutePath() {
    return wrapped.absolutePath();
  }

  public Path path() {
    return wrapped.path();
  }

  public InputStream inputStream() throws IOException {
    return wrapped.inputStream();
  }

  public String contents() throws IOException {
    return wrapped.contents();
  }

  public String fileName() {
    return path().getFileName().toString();
  }

  public TextRange selectLine(int line) {
    return wrapped.selectLine(line);
  }

  public Charset charset() {
    return wrapped.charset();
  }
}
