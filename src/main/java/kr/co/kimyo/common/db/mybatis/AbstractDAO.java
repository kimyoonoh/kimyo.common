package kr.triniti.common.db.mybatis;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * kr.triniti.common.db.mybatis
 * AbstractDAO.java
 * </pre>
 * @Author  : Kim yunoh
 * @Date    : 2021. 3. 3.
 * @Version : 1.0.0
 */
public class AbstractDAO implements DAO {
    protected Logger log = LoggerFactory.getLogger(AbstractDAO.class);
     
    @Autowired
    private SqlSessionTemplate sqlSession;
    

     /**
     * <pre>
     *  설명 : 쿼리 실행 로그를 찍는다. 로그 레벨이 Debug일 경우에만 동작함.
     * </pre>
     * @Method Name : printQueryId
     * @param queryId : 쿼리 ID
     */
    protected void printQueryId(String queryId) {
        if (log.isDebugEnabled()) log.debug("\t QueryId  \t:  " + queryId);
    }
     
    public Object insert(String queryId, Object params){
    	printQueryId(queryId);
    	return sqlSession.insert(queryId, params);
    }
    
    public Object insert(String queryId){
        printQueryId(queryId);
        return sqlSession.insert(queryId);
    }
     
    public Object update(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
    
    public Object update(String queryId){
        printQueryId(queryId);
        return sqlSession.update(queryId);
    }
     
    public Object delete(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }
    
    public Object delete(String queryId){
        printQueryId(queryId);
        return sqlSession.delete(queryId);
    }
    
    public Object view(String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }
     
    public Object view(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }
     
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List list(String queryId){
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List list(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }

	public String query(String queryId, Object param) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> paging(String queryId, int pageSize, int pageNo) {
		return paging(queryId, null, pageSize, pageNo);
	}

	public List<?> paging(String paramString, Object paramObject, int paramInt1, int paramInt2) {
		// TODO Auto-generated method stub
		return null;
	}
}