package com.nuevospa.tasksmanagement.utilies;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Check {

	public static boolean isNull(final Object object) {
		return object == null;
	}

	public static boolean isNotNull(final Object object) {
		return !isNull(object);
	}

	public static boolean isNotEmptyString(final String str) {
		return null != str && !str.trim().isEmpty();
	}
}
