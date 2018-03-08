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

import java.io.IOException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Metric;
import org.xml.sax.SAXException;

import com.nami.sonar.rule.checks.XmlFile;
import com.nami.sonar.rule.compat.CompatibleInputFile;
import com.nami.sonar.rule.parsers.LineCountParser;
import com.nami.sonar.rule.parsers.ParseException;

/**
 * Count lines of code in XML files.
 *
 * @author Matthijs Galesloot
 */
public final class LineCounter {

  private static final Logger LOG = LoggerFactory.getLogger(LineCounter.class);

  private LineCounter() {
  }

  private static void saveMeasures(XmlFile xmlFile, LineCountData data, FileLinesContext fileLinesContext, SensorContext context) {
    data.updateAccordingTo(xmlFile.getLineDelta());

    for (int line = 1; line <= data.linesNumber(); line++) {
      fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, data.linesOfCodeLines().contains(line) ? 1 : 0);
      fileLinesContext.setIntValue(CoreMetrics.COMMENT_LINES_DATA_KEY, line, data.effectiveCommentLines().contains(line) ? 1 : 0);
    }
    fileLinesContext.save();

    saveMeasure(context, xmlFile.getInputFile(), CoreMetrics.COMMENT_LINES, data.effectiveCommentLines().size());
    saveMeasure(context, xmlFile.getInputFile(), CoreMetrics.NCLOC, data.linesOfCodeLines().size());
  }

  private static <T extends Serializable> void saveMeasure(SensorContext context, CompatibleInputFile inputFile, Metric<T> metric, T value) {
    context.<T>newMeasure()
      .withValue(value)
      .forMetric(metric)
      .on(inputFile.wrapped())
      .save();
  }

  public static void analyse(SensorContext context, FileLinesContextFactory fileLinesContextFactory, XmlFile xmlFile) {
    LOG.debug("Count lines in " + xmlFile.getAbsolutePath());

    try {
      saveMeasures(
        xmlFile,
        new LineCountParser(xmlFile.getContents(), xmlFile.getCharset()).getLineCountData(),
        fileLinesContextFactory.createFor(xmlFile.getInputFile().wrapped()), context);
    } catch (IOException | SAXException e) {
      LOG.debug("Unable to count lines for file " + xmlFile.getAbsolutePath());
      throw new ParseException(e);
    }
  }

}
