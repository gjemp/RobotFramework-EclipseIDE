/**
 * Copyright 2011 Nitor Creations Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.otherone.robotframework.eclipse.editor.builder;

import java.util.Collections;
import java.util.List;

import org.otherone.robotframework.eclipse.editor.builder.info.IDynamicParsedKeywordString;
import org.otherone.robotframework.eclipse.editor.builder.info.IDynamicParsedString;

/**
 * An immutable implementation of all the I*String interfaces in the ...builder.info package.
 * 
 * @author xkr47
 */
public class ParsedString implements IDynamicParsedKeywordString {

  private static String[] STRIPPABLE_PREFIXES = { "given ", "when ", "then ", "and " };

  private final String value;
  private final int argCharPos;
  private final List<IDynamicParsedString> parts;

  public ParsedString(String value, int argCharPos) {
    if (value == null) {
      throw new NullPointerException("value");
    }
    if (value.isEmpty()) {
      throw new IllegalArgumentException("value is empty");
    }
    if (argCharPos < 0) {
      throw new IllegalArgumentException("argCharPos < 0");
    }
    this.value = value;
    this.argCharPos = argCharPos;
    this.parts = null;
  }

  /**
   * @param parts
   *          automatically wrapped using {@link Collections#unmodifiableList(List)}
   */
  public ParsedString(String value, int argCharPos, List<? extends IDynamicParsedString> parts) {
    if (value == null) {
      throw new NullPointerException("value");
    }
    if (value.isEmpty()) {
      throw new IllegalArgumentException("value is empty");
    }
    if (argCharPos < 0) {
      throw new IllegalArgumentException("argCharPos < 0");
    }
    if (parts == null) {
      throw new NullPointerException("parts");
    }
    if (parts.isEmpty()) {
      throw new IllegalArgumentException("parts list is empty");
    }
    this.value = value;
    this.argCharPos = argCharPos;
    this.parts = Collections.unmodifiableList(parts);
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public int getArgCharPos() {
    return argCharPos;
  }

  @Override
  public int getArgEndCharPos() {
    return argCharPos + value.length();
  }

  @Override
  public List<IDynamicParsedString> getParts() {
    // this method should not be visible to the user if "parts" is not defined
    assert parts != null;
    return parts;
  }

  @Override
  public String getAlternateValue() {
    String lcValue = value.toLowerCase();
    for (String strippablePrefix : STRIPPABLE_PREFIXES) {
      if (lcValue.startsWith(strippablePrefix)) {
        return value.substring(strippablePrefix.length());
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return parts == null ? '"' + value + '"' : parts.toString();
  }

  @Override
  public String getDebugString() {
    return toString() + " @" + argCharPos + "-" + (getArgEndCharPos() - 1);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return value.equals(obj);
  }

}
