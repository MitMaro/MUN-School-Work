/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import ca.mitmaro.ldb.exception.InvalidListException;

/**
 * An abstract paper
 * 
 * @author Tim Oram (MitMaro)
 */
public abstract class Paper implements Serializable {
	
	/**
	 * The serialization id
	 */
	private static final long serialVersionUID = -4345814402579311723L;

	/**
	 * The data context for the paper
	 */
	protected static class Context implements Serializable {
		
		/**
		 * The serialization id
		 */
		private static final long serialVersionUID = 32075534600184931L;
		
		/**
		 * The titke
		 */
		protected String title;
		/**
		 * The authors first name
		 */
		protected String author_first;
		/**
		 * The authors last name
		 */
		protected String author_last;
		/**
		 * The year
		 */
		protected int year;
		
		/**
		 * Constructs an empty context
		 */
		protected Context() {}
		
		/**
		 * Constructs a context using an UpdateContext for information
		 * @param context
		 */
		protected Context(UpdateContext context) {
			this.update(context);
		}
		
		/**
		 * Updates this context using another context for information
		 * @param context Another context
		 */
		protected void update(Context context) {
			this.title = context.title;
			this.author_first = context.author_first;
			this.author_last = context.author_last;
			this.year = context.year;
		}
		
		/**
		 * Updates this context using an update context
		 * @param context The update context
		 */
		protected void update(UpdateContext context) {
			if (context.author_first != null) {
				this.author_first = context.author_first;
			}
			
			if (context.author_last != null) {
				this.author_last = context.author_last;
			}
			
			if (context.title != null) {
				this.title = context.title;
			}
			
			if (context.year != -1) {
				this.year = context.year;
			}
		}
	}
	
	/**
	 * The unique id of this paper 
	 */
	private String id;

	/**
	 * The referencing papers of the paper
	 */
	private LinkedHashSet<Paper> references;
	/**
	 * The citing papers of the paper
	 */
	private LinkedHashSet<Paper> citations;
	
	/**
	 * The dataset of the paper, indexed by list name
	 */
	protected LinkedHashMap<String, Context> data_sets;
	
	/**
	 * Constructs a paper using an id
	 * 
	 * @param id
	 */
	public Paper (String id) {
		this.id = id;
		this.data_sets = new LinkedHashMap<String, Context>();
		this.references = new LinkedHashSet<Paper>();
		this.citations = new LinkedHashSet<Paper>();
	}
	
	/**
	 * Sets the dataset for a list using an update context
	 * 
	 * @param list_name The name of the list
	 * @param context The update context
	 */
	protected void setDataSet (String list_name, UpdateContext context) {
		Context c = this.getContext();
		c.update(context);
		this.data_sets.put(list_name, c);
	}
	
	/**
	 * Copies the data from one list to another
	 * 
	 * @param new_list The new list name
	 * @param old_list The old list name
	 * @throws InvalidListException If the data indexed by list name doesn't exist
	 */
	public void addToList(String new_list, String old_list) throws InvalidListException {
		if (this.data_sets.containsKey(old_list)) {
			Context context = this.getContext();
			context.update(this.data_sets.get(old_list));
			this.data_sets.put(new_list, context);
		} else {
			throw new InvalidListException(old_list);
		}
	}
	
	/**
	 * @return The unique id for the paper
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Gets the paper title in respect to the list
	 * 
	 * @param list_name The name of the list
	 * @return The title
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getTitle(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).title;
		}
		throw new InvalidListException(list_name);
	}
	
	/**
	 * Gets the authors first name in respect to the list
	 * 
	 * @param list_name The name of the list
	 * @return The authors first name
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getAuthorFirst(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).author_first;
		}
		throw new InvalidListException(list_name);
	}
	
	/**
	 * Gets the authors last name in respect to the list
	 * 
	 * @param list_name The name of the list
	 * @return The authors last name
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getAuthorLast(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).author_last;
		}
		throw new InvalidListException(list_name);
	}

	
	/**
	 * Gets the year in respect to the list
	 * 
	 * @param list_name The name of the list
	 * @return The year
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public int getYear(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).year;
		}
		throw new InvalidListException(list_name);
	}

	public boolean inList(String list_name) {
		return this.data_sets.containsKey(list_name);
	}

	public void removeList(String list_name) {
		this.data_sets.remove(list_name);
	}
	
	/**
	 * Updates the data for a list, if the list doesn't exist it's created
	 * 
	 * @param list_name The name of the list
	 * @param context The update context
	 */
	public void update(String list_name, UpdateContext context) {
		if (!this.data_sets.containsKey(list_name)) {
			this.data_sets.put(list_name, this.getContext());
		}
		this.data_sets.get(list_name).update(context);
	}
	
	/**
	 * Adds a paper as a reference to this one
	 * 
	 * @param paper The paper that is referenced
	 */
	public void addReference(Paper paper) {
		this.references.add(paper);
	}

	public void removeReference(Paper paper) {
		this.references.remove(paper);
	}
	
	/**
	 * @return This papers references
	 */
	public LinkedHashSet<Paper> getReferences() {
		return this.references;
	}

	/**
	 * Add a paper as a citation to this one
	 * 
	 * @param paper The paper to cite
	 */
	public void addCitation(Paper paper) {
		this.citations.add(paper);
	}
	
	public void removeCitation(Paper paper) {
		this.citations.remove(paper);
	}
	
	/**
	 * @return The papers citations
	 */
	public LinkedHashSet<Paper> getCitations() {
		return this.citations;
	}

	public void clearCitations() {
		this.citations = new LinkedHashSet<Paper>();
	}
	
	public void clearReferences() {
		this.references = new LinkedHashSet<Paper>();
	}
	
	/**
	 * Gets a list this paper is in (usually the first list) 
	 * 
	 * @return
	 */
	public String getList() {
		return this.data_sets.keySet().iterator().next();
	}
	
	@Override
	public boolean equals(Object obj) {

		/* Tim's Rant
			For some brain dead reason the JVM calls hashCode and equals before populating a
			the class with data when doing object deserialization. This completely breaks
			the Set data structure on objects that use field data in those methods. I would
			think this is common practice for objects that would have a primary id, like
			entities in ORMs.
			
			Perhaps there is something I am unaware of but I can't see why there is a need
			to call these during deserialization, specially before populating the fields of
			the object.
			
			Another addition to my list of why Java is broken.
			
			
			Update (I was slightly off in my rant):
			
			More information: http://bugs.sun.com/view_bug.do?bug_id=6208166
			
			This fix completely breaks HashSets, there is a fix in Application.java to fix
			the problems this causes.
			
			See: http://bugs.sun.com/view_bug.do?bug_id=4957674
		*/
		if (this.getId() == null) {
			return super.equals(obj);
		}
		return obj instanceof Paper && ((Paper) obj).getId().equals(this.getId());
	}
	
	@Override
	public int hashCode() {
		
		// see equals
		if (this.getId() == null) {
			return super.hashCode();
		}
		
		return this.getId().hashCode();
	}
	
	/**
	 * Get a printable representation of this paper
	 * 
	 * @param list_name The name of the list
	 * @param padding Padding to add to each line of the returned text
	 * @return
	 * @throws InvalidListException If the list doesn't exist
	 */
	abstract public String getPrintable(String list_name, String padding) throws InvalidListException;
	
	/**
	 * The fields of the paper
	 * @return
	 */
	abstract public String[] getFields();
	
	/**
	 * The papers context
	 * @return
	 */
	abstract protected Context getContext();
	
}
