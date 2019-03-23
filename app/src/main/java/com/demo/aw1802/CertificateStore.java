/*
 * AirWatch Android Software Development Kit
 * Copyright (c) 2013 AirWatch. All rights reserved.
 *
 * Unless required by applicable law, this Software Development Kit and sample
 * code is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. AirWatch expressly disclaims any and all
 * liability resulting from User's use of this Software Development Kit. Any
 * modification made to the SDK must have written approval by AirWatch.
 */

package com.demo.aw1802;

import com.airwatch.bizlib.model.CertificateDefinition;


public class CertificateStore {

	private static CertificateDefinition certStore = null;
	
	private static String profileId = null;
	
	public static void addCert(CertificateDefinition cert) {
		certStore = cert;
	}
	
	public static CertificateDefinition getCert() {
		if (certStore==null)
			return null;
		else
			return certStore;
	}
	
	public static void addProfileId(String id) {
		profileId = id;
	}
	
	public static String getProfileId() {
		if (profileId==null)
			return null;
		else
			return profileId;
	}
}
