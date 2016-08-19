package com.gpdi.searchengine.commonservice.api.entity.index;

import java.io.Serializable;



/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用) 代表需要索引的字段，转换为Field
 * @author zhangwu
 * @date 2016年8月8日
 * @version 1.0.0
 */
public class DocField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldName = "body";
	private String fieldValue = "body";
	// 是否存储
	private Store store = Store.YES;
	// 是否索引、分词
	private Index index = Index.ANALYZED;
	// 字段类型
	private DocFieldType docFieldType = DocFieldType.STRING;

	private float boost = 1.0f;

	public DocField(String fieldName, String fieldValue) {
		this(fieldName, fieldValue, Store.YES, Index.ANALYZED,
				DocFieldType.STRING);
	}

	public DocField(String fieldName, String fieldValue, Store store) {
		this(fieldName, fieldValue, store, Index.ANALYZED, DocFieldType.STRING);
	}

	public DocField(String fieldName, String fieldValue, Store store,
			Index index) {
		this(fieldName, fieldValue, store, index, DocFieldType.STRING);
	}

	public DocField(String fieldName, String fieldValue, Store store,
			Index index, DocFieldType docFieldType) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.store = store;
		this.index = index;
		this.docFieldType = docFieldType;
	}

	/** 字段类型，用于映射lucene */
	public static enum DocFieldType {
		STRING, NUMERIC;
	}

	/** Specifies whether and how a field should be stored. */
	public static enum Store {

		/**
		 * Store the original field value in the index. This is useful for short
		 * texts like a document's title which should be displayed with the
		 * results. The value is stored in its original form, i.e. no analyzer
		 * is used before it is stored.
		 */
		YES {
			@Override
			public boolean isStored() {
				return true;
			}
		},

		/** Do not store the field value in the index. */
		NO {
			@Override
			public boolean isStored() {
				return false;
			}
		};

		public abstract boolean isStored();
	}

	/** Specifies whether and how a field should be indexed. */
	public static enum Index {

		/**
		 * Do not index the field value. This field can thus not be searched,
		 * but one can still access its contents provided it is
		 * {@link Field.Store stored}.
		 */
		NO {
			@Override
			public boolean isIndexed() {
				return false;
			}

			@Override
			public boolean isAnalyzed() {
				return false;
			}

			@Override
			public boolean omitNorms() {
				return true;
			}
		},

		/**
		 * Index the tokens produced by running the field's value through an
		 * Analyzer. This is useful for common text.
		 */
		ANALYZED {
			@Override
			public boolean isIndexed() {
				return true;
			}

			@Override
			public boolean isAnalyzed() {
				return true;
			}

			@Override
			public boolean omitNorms() {
				return false;
			}
		},

		/**
		 * Index the field's value without using an Analyzer, so it can be
		 * searched. As no analyzer is used the value will be stored as a single
		 * term. This is useful for unique Ids like product numbers.
		 */
		NOT_ANALYZED {
			@Override
			public boolean isIndexed() {
				return true;
			}

			@Override
			public boolean isAnalyzed() {
				return false;
			}

			@Override
			public boolean omitNorms() {
				return false;
			}
		},

		/**
		 * Expert: Index the field's value without an Analyzer, and also disable
		 * the indexing of norms. Note that you can also separately
		 * enable/disable norms by calling {@link Field#setOmitNorms}. No norms
		 * means that index-time field and document boosting and field length
		 * normalization are disabled. The benefit is less memory usage as norms
		 * take up one byte of RAM per indexed field for every document in the
		 * index, during searching. Note that once you index a given field
		 * <i>with</i> norms enabled, disabling norms will have no effect. In
		 * other words, for this to have the above described effect on a field,
		 * all instances of that field must be indexed with
		 * NOT_ANALYZED_NO_NORMS from the beginning.
		 */
		NOT_ANALYZED_NO_NORMS {
			@Override
			public boolean isIndexed() {
				return true;
			}

			@Override
			public boolean isAnalyzed() {
				return false;
			}

			@Override
			public boolean omitNorms() {
				return true;
			}
		},

		/**
		 * Expert: Index the tokens produced by running the field's value
		 * through an Analyzer, and also separately disable the storing of
		 * norms. See {@link #NOT_ANALYZED_NO_NORMS} for what norms are and why
		 * you may want to disable them.
		 */
		ANALYZED_NO_NORMS {
			@Override
			public boolean isIndexed() {
				return true;
			}

			@Override
			public boolean isAnalyzed() {
				return true;
			}

			@Override
			public boolean omitNorms() {
				return true;
			}
		};
		public abstract boolean isIndexed();

		public abstract boolean isAnalyzed();

		public abstract boolean omitNorms();
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public Store getStore() {
		return store;
	}

	public Index getIndex() {
		return index;
	}

	public DocFieldType getDocFieldType() {
		return docFieldType;
	}

	public float getBoost() {
		return boost;
	}

	@Override
	public String toString() {
		return "DocField [fieldName=" + fieldName + ", fieldValue="
				+ fieldValue + ", store=" + store + ", index=" + index
				+ ", docFieldType=" + docFieldType + ", boost=" + boost + "]";
	}

}
