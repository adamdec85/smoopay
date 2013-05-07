package com.smoopay.sts.utils;

import org.springframework.stereotype.Service;

@Service
public class VirtualAccIdCreator {

	public String create(Long clientId, String bankName) {
		return clientId + "-" + bankName;
	}
}