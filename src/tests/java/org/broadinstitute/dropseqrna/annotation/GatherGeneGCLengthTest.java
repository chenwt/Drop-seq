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
package org.broadinstitute.dropseqrna.annotation;

import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.reference.ReferenceSequenceFile;
import htsjdk.samtools.reference.ReferenceSequenceFileFactory;
import htsjdk.samtools.reference.ReferenceSequenceFileWalker;
import htsjdk.samtools.util.SequenceUtil;
import org.broadinstitute.dropseqrna.annotation.GatherGeneGCLength.GCIsoformSummary;
import org.broadinstitute.dropseqrna.annotation.GatherGeneGCLength.GCResult;
import org.testng.Assert;
import org.testng.annotations.Test;
import picard.util.TabbedTextFileWithHeaderParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GatherGeneGCLengthTest {

	// tested in Jim Nemesh's notebook by hand.  Sequences BLAT'd against hg19 to verify location/orientation.
	@Test
	public void testPositiveStrandGene () {
		GatherGeneGCLength d = new GatherGeneGCLength();
		/**
		 * RAB42-001
		 *   C   G   GC  ALL
		 * 518 522 1040 1950
		 */
		String t1= "AAGAAATGAGTGAGGTGGATGCTTCAGCGCTGTTAGTCTCCTCCCCATGCCCCTCCTCACTTCATCTAGTTTAGTCCCTTTATCCTGTGAAGTAGGGGTCATCATTAGCCCCCTTTTACAGAGGAGAGAATTGAGGCTTCGAGAGAGAGAAACTTGGCCAGGAGTTTCCACTCGGTCCGACGCCCTCGGTGCCCCGCCGGGTGCATCACCAGGTCCTTTTACCGGAATGTGGTGGGTGTCCTGCTGGTCTTTGATGTGACAAACAGGAAGTCCTTTGAACACATCCAAGACTGGCACCAGGAGGTCATGGCCACTCAGGGCCCGGACAAGGTCATCTTCCTGCTGGTTGGCCACAAGAGTGACCTGCAGAGCACCCGCTGTGTCTCAGCCCAGGAGGCCGAGGAGCTAGCTGCCTCCCTGGGCATGGCCTTCGTGGAGACCTCGGTTAAAAACAACTGCAATGTGGACCTGGCCTTTGACACCCTCGCTGATGCTATCCAGCAGGCCCTGCAGCAGGGGGACATCAAGCTAGAAGAGGGCTGGGGGGGTGTCCGGCTCATCCACAAGACCCAAATCCCCAGGTCCCCCAGCAGGAAGCAGCACTCAGGCCCATGCCAGTGTTGACTCTAGGAGAGAAAGGGTTAAAGCAGTCCCAGCCTTAGCCCACCTGGTGGGATGGGGAGTGTTAATATCTCTCTGGAGGACAAATGACAGAAGGGTTCATATAAACAGTATCCTGACACAGTCATGCTTCCTGGATTTTGGAGTCGAGGCTTTCTACAGAAAAGAAAGTTCTGATGGCCAGGCATGGTGGCTCACGCCTGTAATCCTAGCATTTTTGGAGGCCAAGGACAGTGGATCACCTGAGGTCAGGGGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCTGTCTCTACTAAAAATACAAAAATTAGCCAGGCGTGGTGGTGCATGCCTGTAATCCCAGTTACTCCAGAGGCTAAGGCAGGAGAATTGCTTGAACCTGGGAGGCAGAGATTGCAGTGAGCCAAGACTGCGCCACTGCACTCCAGCCTGGGCAACAGAGTGAGACTCTGTTTCAAAAAAAAAAAAGAAAAGAAAAGAAAGGCCTGAGAGACCAGATGTGCAACTTCCTGTCCTTGAGCCTCAGTGTCCCTATCTATCGATGGGGCTCATAAAAGATCCCACCTTGAAGGGAGGTGGTGACCACAAATGAGACAGTGGACAGGATGTGCTCACCCAGAGCCTGCCGCGCTGTGAATTGAATGACAAAAGCTCTCATTCCCACTCCCTTTTTCTTGGCTGCGATGTGGCCACTCTGGCAGCATTCCTGGGCTCAGACACTGAGAAGCCAGCGTCAGGAAGCTGATGCATGGGCAAAGGCAGGTGCGGGGAATTCCAGGGGGAGCTTGGCTTGGAGGCTTCTTATGTCCTCAGGCTAAAATGATTCTGGGCATGGGATTAATATGTGACGTCAAACCCAGGGTTGCTGGCCAATGCCCCCCCGACCAGGCCCAGGGGCTGAAAAATGGATGTTGGAGGCTGGGATGAACATGAATGTGTAGCAACTATGTTGGGCACACAGTGGCCACTGTGATGAGCCACCAAGATCCCCCTTTCTGGCTGGGGAACCCATCAACCCTCTCCCCAGCTGCTGGAGTGCCACTGGATGATGGACTTCAGCTTGCCCCACTCTCTGGGAAAGGCCCTCCCTTCAGGGCAGCTTGTATCCAAAGTTCATCTCCTGGGGGGCCTTAAAGGACTCCCTCTTGCCCCAGCTCTGGACAACTCTGAAAGTCAAAACCAACTTTATCAGTCTCTGTGGGCTTCATTGAGGACACTGTTGTGACATCATAGCCAAGTTATCCCCTTGCCCAATCCTGCTTCCTTTTCTTCCCCAAACAGGTATCCATTTCAAGAATATCCCCTAATAAACATCTGCACACTCA";
		GCResult gc1 = d.new GCResult(t1);
		Assert.assertEquals(26.5641, gc1.getCPercent(), 0.1);
		Assert.assertEquals(26.76923, gc1.getGPercent(), 0.1);
		Assert.assertEquals(53.33333, gc1.getGCPercent(), 0.1);
		Assert.assertEquals(1950, gc1.getRegionLength());
		/**
		 * RAB42-002
		*   C   G  GC ALL
		* 241 271 512 837
		*/
		String t2 = "GAGGCCGAGGGCTGCCGCTACCAATTTCGGGTCGCGCTGCTGGGGGACGCGGCGGTGGGCAAGACGTCGCTGCTGCGGAGCTACGTGGCAGGCGCGCCTGGCGCCCCGGAGCCGGAGCCCGAGCCCGAGCCCACGGTGGGCGCCGAGTGCTACCGCCGCGCGCTGCAGCTGCGGGCCGGGCCGCGGGTCAAGCTGCAACTCTGGGACACCGCGGGCCACGAGCGCTTCAGGTGCATCACCAGGTCCTTTTACCGGAATGTGGTGGGTGTCCTGCTGGTCTTTGATGTGACAAACAGGAAGTCCTTTGAACACATCCAAGACTGGCACCAGGAGGTCATGGCCACTCAGGGCCCGGACAAGGTCATCTTCCTGCTGGTTGGCCACAAGAGTGACCTGCAGAGCACCCGCTGTGTCTCAGCCCAGGAGGCCGAGGAGCTAGCTGCCTCCCTGGGCATGGCCTTCGTGGAGACCTCGGTTAAAAACAACTGCAATGTGGACCTGGCCTTTGACACCCTCGCTGATGCTATCCAGCAGGCCCTGCAGCAGGGGGACATCAAGCTAGAAGAGGGCTGGGGGGGTGTCCGGCTCATCCACAAGACCCAAATCCCCAGGTCCCCCAGCAGGAAGCAGCACTCAGGCCCATGCCAGTGTTGACTCTAGGAGAGAAAGGGTTAAAGCAGTCCCAGCCTTAGCCCACCTGGTGGGATGGGGAGTGTTAATATCTCTCTGGAGGACAAATGACAGAAGGGTTCATATAAACAGTATCCTGACACAGTCATGCTTCCTGGATTTTGGAGTCGAGGCTTTCTACAGAAAAGAAAGTTCTGATGGCCAGGC";

		GCResult gc2 = d.new GCResult(t2);
		Assert.assertEquals(28.79331, gc2.getCPercent(), 0.1);
		Assert.assertEquals(32.37754, gc2.getGPercent(), 0.1);
		Assert.assertEquals(61.17085, gc2.getGCPercent(), 0.1);
		Assert.assertEquals(837, gc2.getRegionLength());

		List<GCResult> list = new ArrayList<GCResult>();
		list.add(gc1);
		list.add(gc2);

		GCIsoformSummary s = d.new GCIsoformSummary(null, list);
		Assert.assertEquals(s.getMedianC(), 27.67871, 0.1);
		Assert.assertEquals(s.getMedianG(), 29.57339, 0.1);
		Assert.assertEquals(s.getMedianGC(), 57.25209, 0.1);
		Assert.assertEquals(s.getMedianTranscriptLength(), 1394);

	}

	@Test
	public void testNegativeStrandGene () {
		GatherGeneGCLength d = new GatherGeneGCLength();

		//SNIP1-001
		String t1="GTTAAACTCGTCATTTCCTCCAGCTAGAGGAGCTCAACTGATCTGTTTTCTTTCGCCCAGCCAAAATCACAGAATGAAGGCGGTGAAGAGCGAACGGGAGCGAGGGAGCCGGCGAAGACACCGGGACGGGGACGTGGTGCTGCCGGCGGGGGTGGTGGTGAAGCAGGAGCGTCTCAGCCCAGAAGTCGCACCTCCCGCCCACCGCCGTCCGGACCACTCCGGTGGTAGCCCGTCTCCGCCGACCAGCGAGCCGGCCCGCTCGGGCCACCGCGGGAACCGAGCCCGAGGAGTTAGCCGGTCCCCACCCAAAAAGAAAAACAAGGCCTCAGGGAGAAGAAGCAAGTCTCCTCGCAGTAAGAGAAACCGAAGTCCTCACCACTCAACAGTCAAAGTGAAGCAGGAGCGTGAGGATCATCCCCGGAGAGGACGGGAGGATCGGCAGCACAGGGAACCATCAGAACAGGAACACAGGAGAGCTAGGAACAGTGACCGGGACAGACACCGGGGCCATTCCCACCAAAGGAGAACGTCTAACGAGAGGCCTGGGAGTGGGCAGGGTCAGGGACGGGATCGAGACACTCAGAACCTGCAGGCTCAGGAAGAAGAGCGGGAGTTTTATAATGCCAGGCGACGGGAGCATCGCCAGAGGAATGACGTTGGTGGTGGCGGCAGTGAGTCTCAGGAGTTGGTTCCTCGGCCTGGTGGCAACAACAAAGAAAAAGAGGTGCCCGCTAAAGAAAAACCAAGCTTTGAACTTTCTGGGGCACTTCTTGAGGACACCAACACTTTCCGGGGTGTAGTCATTAAATATAGTGAGCCCCCAGAAGCACGTATCCCCAAAAAACGGTGGCGTCTCTACCCATTTAAAAATGATGAGGTGCTTCCAGTCATGTACATACATCGACAGAGTGCGTACCTACTGGGTCGACACCGCCGCATTGCAGACATTCCAATTGATCACCCGTCTTGTTCAAAGCAGCATGCGGTCTTTCAATATCGGCTTGTGGAATATACCCGTGCTGATGGCACAGTTGGCCGAAGAGTGAAGCCCTACATCATTGACCTTGGCTCAGGCAATGGAACCTTCTTAAACAACAAACGTATTGAGCCACAGAGATACTATGAACTAAAAGAAAAGGATGTACTCAAATTTGGATTCAGTAGCAGAGAATACGTCTTGCTCCATGAGTCGTCGGACACTTCTGAAATAGACAGGAAAGATGACGAGGATGAGGAGGAGGAGGAAGAAGTGTCTGACAGCTAGCAAACTAAGAACCCAAACTATTGATACACGGTTTCCTTCTTGGAAGTCTTTGATTGACTCAGAGAGCACTATGGTGGTGGGTCCAGCACTATGGTGCTCTCTGTAATGCCTCTTACTGCCTTAAGTCTTTCCTCTGTTGCTGACCAGATTGTGTTACCATTTGAATACACTGACTAATGTTTGTTAAACTTTTTCTGTGGCACCTTGGCCACATGCCTGCAGGCATTTGTTTTCAGAACAGTCTCACCAATTACAACACACCGTGTTTTAGTAGAAGTGTTGTGGTTTTAGTTGGTGCTTTCAGAACTGCTGCCTAGGAAACTATAAACCCTTGGTTAAGGGGAAATCATGGCTTGTTCTCTTTGTACAGTTACTTTATTTATATAGGTGTTAAGCTTTGTGGACCAGGTGTTTTTCTTTTGGGGCGAACCCCTGAGCAGAGAATCTTACTAGGCTTTGGTTATCACCAAAACAACCTCCAGTATATACCAAAGCTTTGACTTGTTTGAGCTCTTGAGCTTAGAAGTTGATTTTGCACTTATTTTTTTGGGGGGTGGGAATGTACTGCAGTCAGTAAACATTATTGACTGTTTAACTTAAACAGATGCTTTATGGCACCTGCTCAAGCCCGTGACTGTACAGAAGGATCCTGGTTGCTACCAGTGGGTGCTGATTCAGCATCACAAGTGACTGAAATTGGCTGTGGATCTGTTCTTTGTGAAAGAATTCCTGATTTCTCCATGGAGCATGTACACAACAATTTTGATCATATTAACTGTACTTCAGTTTTGCATTTTTATTCAAATGTTATCTCTTTTTTTCTTTGAGAAATAAACTGTCACTGATGTGACAGCGTTCTTTCTTTATTCTAATAACATGTATAGATCTAAAGCAGGTTGTGTTGTTTACATGTTTCTACACATTTCATCCTTTAAAAAGTTGTTGAGAGAGGTTGTATTTACCTTCCCAAGGTTGGAAAGCAGGGGAATTTCCCAGTGTCCTAGTTTTCCACCAGAGGAATATGTGTAAGTAGCAAAGTATTTGCTGCTTACATATAGTGTGTATGTATGTATATATGTAAATTGTGTGTTAAAGAGCTGATACTGATTTTCATATGACAATGTTAGGCAAAGGCCTCCCTGCATTTGAAGAGCAGGTTTTCATTTATATGTATTTTTGGGATAAAAAAATAAAATTTGTAAATATAGCCCCATTTCAAAGAGTAATGCTTTCCTAGCGTAGAGGACCTTGTGTTCATAGGTCTGTTTGCTGCTAATGGAGCCTTCTGTCAGTACTTTTGAGATAAAAGTAGGTAGCTGTTTTAGAGTGTTGTGGGGCAGGTTATATCGTTGGCCTCCAAGTCAGTGGAATAGCAGATGGTGACATGGAGGTGGGTTGTGGGGAAAGGTGGTGAAGCAGCTCAATCAGTGTTACTACTAATTGTTCCTCTTGGGTTGAGACTTAGGATACTATTAAATTTGATTGTTTTCCATTGTTTTTATACCCATGTGTGCTCAGGGCTAAAGATTGGCTAGTGGGAGGGGCAGCTGACTAATGTTCATTCAGAAGAAGTTAAAAGATCCTTTGAAAAGTGAACACTTAATAAGGTCATTAGATGAAACGTAAGTTGGGCCGGGCATGGGCACAGTGGCTCATGCCTGTAATCCCAACACTTTCGGAGGCTGAGGTGGGCAGATCATCTGAGGTCAGGAGTTCAAGACCAACCTGGCCAACATGGTGAAACCCCATCTCTACTAAAAATACAAAAATTAGCTGGGCGTGGTGGCACATGCCTGTTTTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGTGGAGGTTGCAGCGAGCTGAGATTGTGCCACTGCACTCCAGCCTGGGTGACAGCGAGACTCTGTCTCAGAAAAAAAAAAAAAAGGTAAGTTGAAGAATGTGTTCTACATTGGCCACTGTAACTGCTATTTTCTTCATTTTGGTATGTGCCATAGATCATATATATGTGCTTGTCTGAAAAGTAGGTTTGTTTGGATGAACTTTTCTCTTGATCCAGACTGACGGTTTTCTCTTGAGGGTTTAAGCACTAATCTGGTATCTTACTCTAGGATCTCCTTGTTCAGTCTGTGCTGCAGTAGCACTACTTTGTGGTAGCCTTGAGTTTTGCTACAAACTTCATCCTGGTGGGCACTGTGATAGATGACCACTTTGCCTACATTTTTCAGTAAGAAGTCCTAATTGTTTAGTCAGTTTTTATTTTAGTAGAGGAATAACGTCCTGGATTAAGAAATTCTTTATGCCACATATCTGAGAAGGTGGGTTTTTTTGTTGGTTTGTTTGTTTTGAGATGGCGTCTCACTCTGTCGCCCAGGCTGGAGTGCAGTGGCACCATCTTGGCTCACTGCAACCTCCGCCTCCCAGTTTCAAGCGATTCTCCTGCCTCAGCCTCCCAAGTAGCTGGGACTACAGGCACCTGTCACCAAGCCTGGCTAATTTATATATATAATTTTTATTTTTTATTTTTTTTTAGTAGAGATGGGGTTTCACCATATTGGCCAGGCTGGTCTCGAACTCCTGACCTTGTGATCCGCCCCTCTCAGCCTCCCAAAGTGCTGGGATTACAGGTGTTAGCCACCAGGCCTGGCCTGAGAAGGTGTTTTTTTTTTTTTTTGGTCTTGTTTTCCCTTACCCCTTTCTTTGAAGAAAGGGTGTCAAAAGACATTACCTGCCTCTAAAGCCAGATATCACCAGCAGACTTCTTAAAGTGGACATAATCCTAGAGCTTTCATTCTGTTTAGAAGTTCATGTTCATCTTAGCCATTTGGAACTTCTTCATTCTCTATCTTTTCTCCACGGTGGCTGGGCTTGTCTGCAAATCATTGTGTCAAAATCAAACTATTTTCAAAACAGCCCTTTGCTTCTGAGCCCCTCCCCTAAGTCCTCTGTGGGGGTCCATGATTCTGCAGAGGTATGGGACAGAATCTTCAGATTTTACCCCTTGAGTCTCTTCCTAGTCATATCCTGGTTCCCTCATTCTAATATTGACAAAGGATGACTCATTAAGTGCAACTGGTTATGTAACTTTCAAATACTTTCATTGTGTATGTCAGGATCTGAGGAACAAAATGATGTCATTTAATCGGAATCTAAACGTGACACAAACAACGTGCCAGCAATACCTGCTTGTGAAATAATGTTCTGAGCCCACAGTGTTCCTGGGTATGTGAGTTTATATCAAGTGAAAAGGCTGCTTAATTGACATTAAAGTTTTGGAATGTAAAGCTTCA";
		GCResult gc1 = d.new GCResult(t1);
		Assert.assertEquals(20.8, gc1.getCPercent(), 0.1);
		Assert.assertEquals(24.2, gc1.getGPercent(), 0.1);
		Assert.assertEquals(45.1, gc1.getGCPercent(), 0.1);
		Assert.assertEquals(4563, gc1.getRegionLength());

		//SNIP1-002
		String t2 = "ATTTCCTCCAGCTAGAGGAGCTCAACTGATCTGTTTTCTTTCGCCCAGCCAAAATCACAGAATGAAGGCGGTGAAGAGCGAACGGGAGCGAGGGAGCCGGCGAAGACACCGGGACGGGGACGTGGTGCTGCCGGCGGGGGTGGTGGTGAAGCAGGAGCGTCTCAGCCCAGAAGTCGCACCTCCCGCCCACCGCCGTCCGGACCACTCCGGTGGTAGCCCGTCTCCGCCGACCAGCGAGCCGGCCCGCTCGGGCCACCGCGGGAACCGAGCCCGAGGAGTTAGCCGGTCCCCACCCAAAAAGAAAAACAAGGCCTCAGGGAGAAGAAGCAAGTCTCCTCGCAGTAAGAGAAACCGAAGTCCTCACCACTCAACAGTCAAAGTGAAGCAGGTAAGTGGATTGGGGTGTTTTGATGGGTCCAAATTGAGAGCCTTTTCCTATCACAGCTAAAGAGGAATGTGTGTGTGTGTTGTGTGTGTGCACACATGTGCACGTACGCTCGCCTACATCTCTACTTGTACCTCCGATGGGCTTTTCTGAAAACTGGC";
		GCResult gc2 = d.new GCResult(t2);
		Assert.assertEquals(27.8, gc2.getCPercent(), 0.1);
		Assert.assertEquals(30.2, gc2.getGPercent(), 0.1);
		Assert.assertEquals(58.1, gc2.getGCPercent(), 0.1);
		Assert.assertEquals(546, gc2.getRegionLength());

		//SNIP1-003
		String t3 = "GCCCAGCCAAAATCACAGAATGAAGGCGGTGAAGAGCGAACGGGAGCGAGGGAGCCGGTCCCCACCCAAAAAGAAAAACAAGGCCTCAGGGAGAAGAAGCAAGTCTCCTCGCAGTAAGAGAAACCGAAGTCCTCACCACTCAACAGTCAAAGTGAAGCAGTCCGTTTTCATGCTGTTGATAAAGACATACCCGAGACCCGGCGATTTACAAAAGAAAGAGGTTTAATGGACTTACAGTTCCACATGGCTGGAGAGGCCTCACAATCATGGTGGGAAGCAAGGAGGAGCAAATCACACGGATGGCGTCAGGCAAGGAGAGATAAAAGAGAGCGTGAGGATCATCCCCGGAGAGGACGGGAGGATCGGCAGCACAGGGAACCATCAGAACAGGAACACAGGAGAGCTAGGAACAGTGACCGGGACAGACACCGGGGCCATTCCCACCAAAGGAGAACGTCTAACGAGAGGCCTGGGAGTGGGCAGGGTCAGGGACGGGATCGAGACACTCAGAACCTGCAGGCTCAGGAAGAAGAGCGGGAGTTTTATAATGCCAGGCGACGGGAGCATCGCCAGAGGAATGACG";
		GCResult gc3 = d.new GCResult(t3);
		Assert.assertEquals(22.8, gc3.getCPercent(), 0.1);
		Assert.assertEquals(31.7, gc3.getGPercent(), 0.1);
		Assert.assertEquals(54.5, gc3.getGCPercent(), 0.1);
		Assert.assertEquals(583, gc3.getRegionLength());

		List<GCResult> list = new ArrayList<GCResult>();
		list.add(gc1);
		list.add(gc2);
		list.add(gc3);

		GCIsoformSummary s = d.new GCIsoformSummary(null, list);
		Assert.assertEquals(s.getMedianC(), 22.8, 0.1);
		Assert.assertEquals(s.getMedianG(), 30.2, 0.1);
		Assert.assertEquals(s.getMedianGC(), 54.5, 0.1);
		Assert.assertEquals(s.getMedianTranscriptLength(), 583);

	}

	private static final File TEST_DATA_DIR = new File("testdata/org/broadinstitute/transcriptome/annotation");
	private static final String REFERENCE_NAME = "ERCC92";
	private static final File FASTA = new File(TEST_DATA_DIR, REFERENCE_NAME + ".fasta.gz");
	private static final File GTF = new File(TEST_DATA_DIR, REFERENCE_NAME + ".gtf.gz");

	@Test
	public void testBasic() throws IOException {
		final GatherGeneGCLength clp = new GatherGeneGCLength();
        final File outputFile = File.createTempFile("GatherGeneGCLengthTest.", ".gc_length_metrics");
        final File outputTranscriptSequencesFile = File.createTempFile("GatherGeneGCLengthTest.", ".transcript_sequences.fasta");
        final File outputTranscriptLevelFile = File.createTempFile("GatherGeneGCLengthTest.", ".transcript_level");
        outputFile.deleteOnExit();
        outputTranscriptSequencesFile.deleteOnExit();
        outputTranscriptLevelFile.deleteOnExit();
        final String[] args = new String[] {
                "GTF=" + GTF.getAbsolutePath(),
                "REFERENCE_SEQUENCE=" + FASTA.getAbsolutePath(),
                "OUTPUT=" + outputFile.getAbsolutePath(),
                "OUTPUT_TRANSCRIPT_SEQUENCES=" + outputTranscriptSequencesFile.getAbsolutePath(),
                "OUTPUT_TRANSCRIPT_LEVEL=" + outputTranscriptLevelFile.getAbsolutePath()
        };
        Assert.assertEquals(clp.instanceMain(args), 0);
        final ReferenceSequence transcriptSequence = ReferenceSequenceFileFactory.getReferenceSequenceFile(outputTranscriptSequencesFile, false).nextSequence();
        final ReferenceSequence genomicSequence = ReferenceSequenceFileFactory.getReferenceSequenceFile(FASTA).nextSequence();
        // For ERCC, the first sequence is the first gene, which is a single exon
        final String geneName = "ERCC-00002";
        final String transcriptName = "DQ459430";
        Assert.assertEquals(transcriptSequence.getBaseString(), genomicSequence.getBaseString());
        Assert.assertEquals(transcriptSequence.getName(), geneName + " " + transcriptName);
        int numG = 0;
        int numC = 0;
        for (byte b : transcriptSequence.getBases()) {
        	if (b == SequenceUtil.G) ++numG;
        	else if (b == SequenceUtil.C) ++numC;
		}
		final double pctG = 100 * numG/(double)transcriptSequence.getBases().length;
        final double pctC = 100 * numC/(double)transcriptSequence.getBases().length;
        final double pctGC = 100 * (numC + numG)/(double)transcriptSequence.getBases().length;
        TabbedTextFileWithHeaderParser.Row metric = new TabbedTextFileWithHeaderParser(outputFile).iterator().next();
        Assert.assertEquals(metric.getField("GENE"), geneName);
        Assert.assertEquals(Integer.parseInt(metric.getField("START")), 1);
        Assert.assertEquals(Integer.parseInt(metric.getField("END")), transcriptSequence.getBases().length);
        Assert.assertEquals(Double.parseDouble(metric.getField("PCT_GC_UNIQUE_EXON_BASES")), pctGC, 0.05);
        Assert.assertEquals(Double.parseDouble(metric.getField("PCT_C_ISOFORM_AVERAGE")), pctC, 0.05);
        Assert.assertEquals(Double.parseDouble(metric.getField("PCT_G_ISOFORM_AVERAGE")), pctG, 0.05);
        Assert.assertEquals(Integer.parseInt(metric.getField("NUM_TRANSCRIPTS")), 1);
        TabbedTextFileWithHeaderParser.Row transcriptLevel = new TabbedTextFileWithHeaderParser(outputTranscriptLevelFile).iterator().next();
        Assert.assertEquals(transcriptLevel.getField("TRANSCRIPT"), transcriptName);
        Assert.assertEquals(transcriptLevel.getField("CHR"), genomicSequence.getName());
        Assert.assertEquals(Integer.parseInt(transcriptLevel.getField("START")), 1);
        Assert.assertEquals(Integer.parseInt(transcriptLevel.getField("END")), transcriptSequence.getBases().length);
        Assert.assertEquals(Double.parseDouble(transcriptLevel.getField("PCT_GC")), pctGC, 0.05);
        Assert.assertEquals(Double.parseDouble(transcriptLevel.getField("PCT_C")), pctC, 0.05);
        Assert.assertEquals(Double.parseDouble(transcriptLevel.getField("PCT_G")), pctG, 0.05);
	}
}
