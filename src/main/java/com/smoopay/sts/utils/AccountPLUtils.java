package com.smoopay.sts.utils;

import org.springframework.stereotype.Service;

@Service
public class AccountPLUtils {

	public long[] getBankIdFromAccount(String account) {
		String temp = account;
		if (account.contains("PL")) {
			temp = account.substring(3);
		}
		temp = temp.substring(3);
		temp = temp.trim();
		temp = temp.replaceAll(" ", "");
		long[] ids = new long[2];
		ids[0] = Long.valueOf(temp.substring(0, 4)).longValue();
		ids[1] = Long.valueOf(temp.substring(0, 8)).longValue();
		return ids;
	}
}