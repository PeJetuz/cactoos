/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import org.cactoos.Func;
import org.cactoos.Text;
import org.cactoos.scalar.Ternary;

/**
 * Text in capitalized case, changed the first character to title case, no other
 * characters are changed.
 *
 * @since 0.46
 */
public final class Capitalized extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param text The text
     */
    public Capitalized(final String text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * @param text The text
     */
    public Capitalized(final Text text) {
        super(
            new TextOf(
                () -> {
                    final Func<String, String> capitalize = cap -> {
                        final Text first = new Sub(cap, 0, 1);
                        final Text upper = new Upper(first);
                        return
                            new Ternary<String>(
                                () -> upper.equals(first),
                                () -> cap,
                                () -> new StringBuilder()
                                    .append(upper)
                                    .append(new Sub(text, 1))
                                    .toString())
                            .value();
                    };
                    return new Ternary<String>(
                        text.asString(),
                        t -> t.isEmpty(),
                        t -> t,
                        t -> capitalize.apply(t)
                            ).value();
                }));
    }
}
