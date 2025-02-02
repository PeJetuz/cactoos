/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.iterator;

import java.util.Collections;
import java.util.Iterator;
import org.cactoos.iterable.IterableOf;

/**
 * A few Iterators joined together.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of item
 * @since 0.1
 */
public final class Joined<T> implements Iterator<T> {

    /**
     * Iterators.
     */
    private final Iterator<Iterator<? extends T>> iters;

    /**
     * Current traversal iterator.
     */
    private Iterator<? extends T> current;

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    @SafeVarargs
    public Joined(final Iterator<? extends T>... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param item First item
     * @param items Iterable
     * @since 0.49
     */
    @SuppressWarnings("unchecked")
    public Joined(final T item, final Iterator<? extends T> items) {
        this(new IterableOf<>(new IteratorOf<>(item), items));
    }

    /**
     * Ctor.
     * @param items Iterable
     * @param item End item
     * @since 0.49
     */
    @SuppressWarnings("unchecked")
    public Joined(final Iterator<? extends T> items, final T item) {
        this(new IterableOf<>(items, new IteratorOf<>(item)));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    public Joined(final Iterable<Iterator<? extends T>> items) {
        this.iters = items.iterator();
        this.current = Collections.emptyIterator();
    }

    @Override
    public boolean hasNext() {
        while (!this.current.hasNext() && this.iters.hasNext()) {
            this.current = this.iters.next();
        }
        return this.current.hasNext();
    }

    @Override
    public T next() {
        this.hasNext();
        return this.current.next();
    }
}
