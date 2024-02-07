package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.common.Utils;
import com.example.demo.entity.Todo;
import com.example.demo.entity.Todo_;
import com.example.demo.form.TodoQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.PageImpl;



@AllArgsConstructor
public class TodoDaoImpl implements TodoDao{
    private final EntityManager entityManager;

    @Override
    public List<Todo>findByJPQL(TodoQuery todoQuery){
        StringBuilder sb = new StringBuilder("SELECT t FROM Todo t WHERE 1=1");

        List<String> params = new ArrayList<>();
        int pos = 0;

        if(todoQuery.getTitle().length() > 0) {
            sb.append(" AND t.title LIKE ?" + (++pos));
            params.add("%" + todoQuery.getTitle() + "%");
        }

        if(todoQuery.getImportance() != -1 ) {
            sb.append(" AND t.importance = ?" + (++pos));
            params.add(String.valueOf(todoQuery.getImportance()));
        }

        if(todoQuery.getUrgency() != -1) {
            sb.append(" AND t.urgency = ?" + (++pos));
            params.add(String.valueOf(todoQuery.getUrgency()));
        }

        if(!todoQuery.getDeadlineFrom().equals("") && !todoQuery.getDeadlineTo().equals("")) {
            sb.append(" AND t.deadline BETWEEN ?" + (++pos) + " AND ?" + (++pos));
            params.add(String.valueOf(todoQuery.getDeadlineFrom()));
            params.add(String.valueOf(todoQuery.getDeadlineTo()));
        }

        if(todoQuery.getDone() != null && todoQuery.getDone().equals("Y")) {
            sb.append(" AND t.done = ?" + (++pos));
            params.add(todoQuery.getDone());
        }

        sb.append(" ORDER BY id ASC");

        Query query = entityManager.createQuery(sb.toString());
        for(int i=0;i<params.size();i++) {
            query.setParameter(i+1, params.get(i));
        }

        @SuppressWarnings("unchecked")
        List<Todo> todoList = query.getResultList();

        return todoList;
    }

    @Override
    public Page<Todo> findByCriteria(TodoQuery todoQuery,Pageable pageable){
        CriteriaBuilder builder =entityManager.getCriteriaBuilder();
        CriteriaQuery<Todo> query = builder.createQuery(Todo.class);
        Root<Todo> root = query.from(Todo.class);
        List<Predicate> predicates = new ArrayList<>();

        //件名
        String title = "";
        if(todoQuery.getTitle().length()>0){
            title = "%" + todoQuery.getTitle() + "%";
        }else{
            title = "%";
        }
        predicates.add( builder.like(root.get(Todo_.TITLE),title));

        //重要度
        if(todoQuery.getImportance() != -1){
            predicates.add( builder.and(builder.equal(root.get(Todo_.IMPORTANCE),todoQuery.getImportance())));
        }

        //緊急度
        if(todoQuery.getUrgency() != -1){
            predicates.add( builder.and(builder.equal(root.get(Todo_.URGENCY),todoQuery.getUrgency())));
        }

        //期限:開始
        if(!todoQuery.getDeadlineFrom().equals("")){
            predicates.add( builder.and(builder.greaterThanOrEqualTo(root.get(Todo_.DEADLINE),Utils.str2date(todoQuery.getDeadlineFrom()))));
        }

        //期限:終了
        if(!todoQuery.getDeadlineTo().equals("")){
            predicates.add( builder.and(builder.lessThanOrEqualTo(root.get(Todo_.DEADLINE),Utils.str2date(todoQuery.getDeadlineTo()))));
        }

        //完了
        if(todoQuery.getDone() != null && todoQuery.getDone().equals("Y")){
            predicates.add( builder.and(builder.equal(root.get(Todo_.DONE),todoQuery.getDone())));
        }

        //SELECT作成
        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);
        query = query.select(root).where(predArray).orderBy(builder.asc(root.get(Todo_.id)));

        TypedQuery<Todo>typedQuery = entityManager.createQuery(query);
        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("null")
        Page<Todo> page = new PageImpl<Todo>(typedQuery.getResultList(),pageable,totalRows);

        return page;
        

    }
}