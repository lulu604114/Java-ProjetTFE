package com.projet.services;

import com.projet.entities.Diary;
import com.projet.entities.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 25/08/2020
 * Time: 13:05
 * =================================================================
 */
public class DiaryService extends Service<Diary> implements Serializable {
    private static final long serialVersionUID = 1L;

    public DiaryService(Class<?> ec) {
        super(ec);
    }

    public List<Diary> getByUser(User user) {
        Map<String, User> param = new HashMap<>();
        param.put("user", user);

        return finder.findByNamedQuery("Diary.findByUser", param);
    }

    @Override
    public Diary save(Diary diary) {
        if (diary.getId() == 0) {
            em.persist(diary);
        } else {
            diary = em.merge(diary);
        }

        return diary;
    }

    public Diary createDefaultDiary() {
        Diary diary = new Diary();
        diary.setLabel("Journal Pro");

        return diary;
    }
}
