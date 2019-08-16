package com.jslsolucoes.nginx.admin.repository.impl;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class RepositoryImpl<T> {

	protected EntityManager entityManager;
	private   Class<T>      clazz;

	@Deprecated
	public RepositoryImpl() {

	}

	@SuppressWarnings("unchecked")
	public RepositoryImpl(EntityManager entityManager) {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityManager = entityManager;
	}

	public List<T> listAll() {
		return listAll(null, null);
	}

	public List<T> listAll(Integer firstResult, Integer maxResults) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		if (firstResult != null && maxResults != null) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	public T load(T entity) {
		Long id = id(entity);
		return id == null ? null : load(id);
	}

	public T load(Long id) {
		return (T) entityManager.find(clazz, id);
	}

	public OperationStatusType insert(T entity) {
		this.entityManager.persist(entity);
		return OperationStatusType.INSERT;
	}

	public OperationStatusType update(T entity) {
		this.entityManager.merge(entity);
		return OperationStatusType.UPDATE;
	}

	private Long id(T entity) {
		Matcher<Field> matcher = new Matcher<Field>() {

			@Override
			public boolean accepts(Field field) {
				return field.isAnnotationPresent(Id.class);
			}
		};
		List<Field> fields = new Mirror().on(clazz).reflectAll().fields().matching(matcher);
		if (CollectionUtils.isEmpty(fields)) {
			throw new RuntimeException("Class" + this.clazz + " doesn't have @Id annotation");
		}
		return (Long) new Mirror().on(entity).invoke().getterFor(fields.get(0));
	}

	public OperationStatusType delete(Long id) {
		return delete(load(id));
	}

	public OperationStatusType delete(T entity) {
		this.entityManager.remove(load(entity));
		return OperationStatusType.DELETE;
	}

	public OperationResult saveOrUpdate(T entity) {
		Long id = id(entity);
		if (id == null) {
			return new OperationResult(insert(entity), id(entity));
		} else {
			return new OperationResult(update(entity), id);
		}
	}

	public Long count() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(clazz).get("id")));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	public void flushAndClear() {
		flush();
		clear();
	}

	public void flush() {
		this.entityManager.flush();
	}

	public void clear() {
		this.entityManager.clear();
	}

}
