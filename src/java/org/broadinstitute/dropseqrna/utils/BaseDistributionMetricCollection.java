/*
 * MIT License
 *
 * Copyright 2017 Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.broadinstitute.dropseqrna.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDistributionMetricCollection implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -55813371198085699L;

	private Map<Integer, BaseDistributionMetric> collection = null;

	public BaseDistributionMetricCollection() {
		collection = new HashMap<>();
	}

	public void addBase (final char base, final int position) {
		BaseDistributionMetric m = this.collection.get(position);
		if (m==null) {
			m = new BaseDistributionMetric();
			this.collection.put(position, m);
		}
		m.addBase(base);
	}

	public void addBases (final String bases) {
		char [] b = bases.toCharArray();
		for (int i=0; i<b.length; i++)
			addBase(b[i], i);
	}

	public void addBases (final char [] bases) {
		for (int i=0; i<bases.length; i++)
			addBase(bases[i], i);
	}

	public void addBases (final byte [] bases) {
		for (int i=0; i<bases.length; i++) {
			char b = (char) bases[i];
			addBase(b, i);
		}
	}

	public BaseDistributionMetric getDistributionAtPosition (final int position) {
		return this.collection.get(position);
	}

	public List<Integer> getPositions () {
		List<Integer> r = new ArrayList<>(this.collection.keySet());
		Collections.sort(r);
		return (r);
	}

	@Override
	public String toString () {
		return this.collection.toString();
	}
}
