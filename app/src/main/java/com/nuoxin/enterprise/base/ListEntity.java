package com.nuoxin.enterprise.base;

import com.nuoxin.enterprise.bean.Entity;

import java.io.Serializable;
import java.util.List;

public interface ListEntity <T extends Entity> extends Serializable {
    List<T> getList();
}
