/*
 * Copyright 2010-2013 Robert J. Buck
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

package com.buck.jsql.literals;

import com.buck.jsql.EvaluationContext;
import com.buck.jsql.EvaluationException;
import com.buck.jsql.Expression;
import com.buck.jsql.ExpressionType;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * A literal for a regular expression pattern.
 */
public class PatternLiteral extends Expression {

    static final private HashSet<Character> REGEXP_CONTROL_CHARS = new HashSet<Character>();

    static {
        REGEXP_CONTROL_CHARS.add('.');
        REGEXP_CONTROL_CHARS.add('\\');
        REGEXP_CONTROL_CHARS.add('[');
        REGEXP_CONTROL_CHARS.add(']');
        REGEXP_CONTROL_CHARS.add('^');
        REGEXP_CONTROL_CHARS.add('$');
        REGEXP_CONTROL_CHARS.add('?');
        REGEXP_CONTROL_CHARS.add('*');
        REGEXP_CONTROL_CHARS.add('+');
        REGEXP_CONTROL_CHARS.add('{');
        REGEXP_CONTROL_CHARS.add('}');
        REGEXP_CONTROL_CHARS.add('|');
        REGEXP_CONTROL_CHARS.add('(');
        REGEXP_CONTROL_CHARS.add(')');
        REGEXP_CONTROL_CHARS.add(':');
        REGEXP_CONTROL_CHARS.add('&');
        REGEXP_CONTROL_CHARS.add('<');
        REGEXP_CONTROL_CHARS.add('>');
        REGEXP_CONTROL_CHARS.add('=');
        REGEXP_CONTROL_CHARS.add('!');
    }

    private final Pattern pattern;

    /**
     * Constructs a pattern to match literals against.
     *
     * @param like   the regular expression pattern
     * @param escape the escape character to use
     */
    public PatternLiteral(final String like, Character escape) {
        super(ExpressionType.PatternLiteral);

        StringBuffer regexp = new StringBuffer(like.length() * 2);
        regexp.append("\\A"); // The beginning of the input
        for (int i = 0; i < like.length(); i++) {
            char c = like.charAt(i);
            if (escape != null && escape == (0xFFFF & c)) {
                i++;
                if (i >= like.length()) {
                    // nothing left to escape...
                    break;
                }

                char t = like.charAt(i);
                regexp.append("\\x");
                regexp.append(Integer.toHexString(0xFFFF & t));
            } else if (c == '%') {
                regexp.append(".*?"); // Do a non-greedy match
            } else if (c == '_') {
                regexp.append('.'); // match one
            } else if (REGEXP_CONTROL_CHARS.contains(Character.valueOf(c))) {
                regexp.append("\\x");
                regexp.append(Integer.toHexString(0xFFFF & c));
            } else {
                regexp.append(c);
            }
        }
        regexp.append("\\z"); // The end of the input

        pattern = Pattern.compile(regexp.toString(), Pattern.DOTALL);
    }

    /**
     * Return the regular expression pattern.
     *
     * @param context unused
     * @return the regular expression to return
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        return pattern;
    }
}
