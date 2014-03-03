/**
 * This is an interface that is going to be used to store a bunch of word buckets, basically. 
 * This way, rather than having to actually create a bunch of word buckets we can have this
 * interface which will hold pointers to a bunch of general ones. Then any class that needs
 * access to a word bucket can simply reference it through here. 
 * 
 * Word buckets will be implemented as arrays of strings. If a very specific word bucket
 * is needed for a template, then it should contain it's own word bucket, rather than
 * using one from here. These are meant to be general purpose word buckets.
 * 
 * We need to be careful when we build these that any of them will work interchangeably in any
 * given sentence. I'm going to make a few example ones to demonstrate what I mean for these.
 * 
 * @author Yasha
 *
 */
public interface WordBuckets 
{
	public String[] emotions = {"happy", "sad", "lonely", "angry", "amused", "content"};
	public String[] exclamations = {"Oh!", "Oye!", "Aha!"};
	public String[] holyBatmans = {"batman", "agility", "banks", "rusted metal"}; 
	
}
