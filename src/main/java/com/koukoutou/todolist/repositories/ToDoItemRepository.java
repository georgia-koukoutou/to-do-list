package com.koukoutou.todolist.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.koukoutou.todolist.models.ToDoItem;

public interface ToDoItemRepository extends CrudRepository<ToDoItem, Long> {
	
	public List<ToDoItem> findByDescription(String description);

}
