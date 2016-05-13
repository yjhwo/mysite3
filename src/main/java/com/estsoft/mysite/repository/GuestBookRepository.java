package com.estsoft.mysite.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.GuestBook;

@Repository
public class GuestBookRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(GuestBook guestbook) {

		guestbook.setReg_date(new Date());
		em.persist(guestbook);
	}

	public List<GuestBook> findAll() {

		TypedQuery<GuestBook> query = em.createQuery("select gb from GuestBook gb order by gb.reg_date",
				GuestBook.class);
		List<GuestBook> list = query.getResultList();
		return list;
	}

	public Boolean remove(GuestBook guestbook) {

		TypedQuery<GuestBook> query = em.createQuery(
				"select gb from GuestBook gb where gb.no = :no and gb.passwd = :passwd", GuestBook.class);
		query.setParameter("no", guestbook.getNo());
		query.setParameter("passwd", guestbook.getPasswd());


		List<GuestBook> list = query.getResultList();
		if (list.size() != 1) {
			return false;
		}

		em.remove(list.get(0));

		return true;
	}
}
