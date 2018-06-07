/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api.f;

/**
 * Represents a function with three parameters and a return value.
 * @param <TParm1> The first parameter class.
 * @param <TParm2> The second parameter class.
 * @param <TParm3> The third parameter class.
 * @param <TResult> The return value class.
 */
public interface Func3<TParm1, TParm2, TParm3, TResult> {
	TResult apply(TParm1 parameter1, TParm2 parameter2, TParm3 parameter3);
}
