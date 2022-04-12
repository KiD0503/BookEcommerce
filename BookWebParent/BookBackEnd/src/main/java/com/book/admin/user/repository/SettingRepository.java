package com.book.admin.user.repository;

import com.book.common.entity.Setting;
import com.book.common.entity.SettingCategory;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends CrudRepository<Setting,String> {
    public List<Setting> findByCategory(SettingCategory category);
}
