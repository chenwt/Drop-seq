package org.broadinstitute.dropseqrna.utils.readiterators;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMTagUtil;
import org.broadinstitute.dropseqrna.utils.FilteredIterator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Filters out a read if the tag value does not match an one of an expected set of values or is not set.
 * If the expected set is null or empty, no reads are filtered.
 * @author nemesh
 *
 */
public class TagValueFilteringIterator<T> extends FilteredIterator<SAMRecord> {
	final short requiredTag;
	final Set<T> expectedValues;

    public TagValueFilteringIterator(final Iterator<SAMRecord> underlyingIterator, final String requiredTag, final Collection<T> expectedValues) {
        super(underlyingIterator);
        this.requiredTag = SAMTagUtil.getSingleton().makeBinaryTag(requiredTag);
        this.expectedValues = new HashSet<T>(expectedValues);
    }



    @Override
    public boolean filterOut(final SAMRecord rec) {
    	Object value = rec.getAttribute(requiredTag);
    	if (value == null)
			return true;

    	if (this.expectedValues.contains(value)) return false;
        return true;
    }
}



