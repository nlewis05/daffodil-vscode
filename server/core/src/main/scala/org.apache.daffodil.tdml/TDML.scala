/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.daffodil.tdml


import java.nio.file._

object TDML {
 def execute(tdmlName: String, tdmlPath: Path): Option[Execute] =
  for {
      ptc <- new Runner(tdmlPath.toUri()).getTS.testCaseMap.get(tdmlName)
      doc <- ptc.document
      file <- doc.documentParts.collectFirst { case fp: FileDocumentPart => fp }
    } yield {
      val schemaPath = Paths.get(ptc.model).normalize()
      val dataPath = Paths
        .get(file.url.toURI())
        .normalize()
      Execute(schemaPath, dataPath)
    }
}
}
