package com.smoopay.sts.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CollectionUtils {

	public <T> List<T> copyIterator(Iterator<T> iter) {
		List<T> copy = new ArrayList<T>();
		while (iter.hasNext()) {
			copy.add(iter.next());
		}
		return copy;
	}
}