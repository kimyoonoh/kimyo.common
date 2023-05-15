package kr.triniti.common.db.mybatis;

import java.util.List;

/**
 * <pre>
 * kr.triniti.common.db.mybatis
 * DAO.java
 * </pre>
 * @Author  : Kim yunoh
 * @Date    : 2021. 3. 3.
 * @Version : 1.0.0
 */
public abstract interface DAO {
	

     /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : query
     * @param queryId : 쿼리 ID
     * @param params  : 쿼리에 포함된 파라미터
     * @return
     *
     * SQL 문장
     */
    public abstract String query(String queryId, Object params);

    
    /**
     * <pre>
     *  설명 : 입력 파라미터 없이 INSERT 쿼리를 실행한다.
     * </pre>
     * @Method Name : insert
     * @param queryId : 쿼리 ID
     * @return
     *
     * Insert 한 레코드 정보(VO)
     */
    public abstract Object insert(String queryId);

    /**
     * <pre>
     *  설명 : INSERT 쿼리를 실행한다.
     * </pre>
     * @Method Name : insert
     * @param queryId : 쿼리 ID
     * @param params : 입력 파라미터
     * @return
     *
     * Insert 한 레코드 정보(VO)
     */
    public abstract Object insert(String queryId, Object params);

    
    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : update
     * @param queryId
     * @return
     *
     * Object
     */
    public abstract Object update(String queryId);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : update
     * @param queryId
     * @param params
     * @return
     *
     * Object
     */
    public abstract Object update(String queryId, Object params);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : delete
     * @param queryId
     * @return
     *
     * Object
     */
    public abstract Object delete(String queryId);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : delete
     * @param queryId
     * @param params
     * @return
     *
     * Object
     */
    public abstract Object delete(String queryId, Object params);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : view
     * @param queryId
     * @return
     *
     * Object
     */
    public abstract Object view(String queryId);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : view
     * @param queryId
     * @param params
     * @return
     *
     * Object
     */
    public abstract Object view(String queryId, Object params);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : list
     * @param queryId
     * @return
     *
     * List<?>
     */
    public abstract List<?> list(String queryId);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : list
     * @param queryId
     * @param params
     * @return
     *
     * List<?>
     */
    public abstract List<?> list(String queryId, Object params);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : paging
     * @param queryId
     * @param pageSize
     * @param pageNo
     * @return
     *
     * List<?>
     */
    public abstract List<?> paging(String queryId, int pageSize, int pageNo);

    /**
     * <pre>
     *  설명 : 
     * </pre>
     * @Method Name : paging
     * @param queryId
     * @param params
     * @param pageSize
     * @param pageNo
     * @return
     *
     * List<?>
     */
    public abstract List<?> paging(String queryId, Object params, int pageSize, int pageNo);
}
