/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

package org.meanbean.test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class BeanVerifiers {

    private final Set<BeanVerifier> verifiers = new HashSet<>();


    protected void addVerifier(BeanVerifier verifier) {
        verifiers.add(verifier);
    }

    /**
     * Customizes bean verification settings. Example:
     * <pre>
     *   BeanVerifier.forClass(Company.class)
     *       .withSettings(settings -&gt; settings.registerFactory(Employee.class, () -&gt; createEmployee())
     *       .withSettings(settings -&gt; settings.setDefaultIterations(10))
     * </pre>
     */
    public BeanVerifiers withSettings(Consumer<VerifierSettings> verifierSettingsEditor) {
        for (BeanVerifier verifier : verifiers) {
            verifier.withSettings(verifierSettingsEditor);
        }
        return this;
    }

    /**
     * Checks the bean's equals and hashCode methods.
     *
     * @see EqualsMethodTester
     * @see HashCodeMethodTester
     */
    public BeanVerifiers verifyEqualsAndHashCode() {
        for (BeanVerifier verifier : verifiers) {
            verifier.verifyEqualsAndHashCode();
        }
        return this;
    }

    /**
     * Checks the bean's public getter and setter methods.
     * <p>
     * Each property is tested by:
     * </p>
     * <ol>
     * <li>generating a random test value for the specific property type</li>
     * <li>invoking the property setter method, passing the generated test value</li>
     * <li>invoking the property getter method and obtaining the return value</li>
     * <li>verifying that the value obtained from the getter method matches the value passed to the setter method</li>
     * </ol>
     *
     * <p>
     * Each property of a type is tested in turn. Each type is tested multiple times to reduce the risk of hard-coded values
     * within a getter or setter matching the random test values generated and the test failing to detect a bug. <br>
     * </p>
     *
     * @see BeanTester
     */
    public BeanVerifiers verifyGettersAndSetters() {
        for (BeanVerifier verifier : verifiers) {
            verifier.verifyGettersAndSetters();
        }
        return this;
    }

    /**
     * Checks that the bean overrides the default to Object::toString method.
     *
     * @see ToStringMethodTester
     */
    public BeanVerifiers verifyToString() {
        for (BeanVerifier verifier : verifiers) {
            verifier.verifyToString();
        }
        return this;
    }

    /**
     * Performs {@link #verifyGettersAndSetters()}, {@link #verifyEqualsAndHashCode()} and {@link #verifyToString()}
     */
    public void verify() {
        for (BeanVerifier verifier : verifiers) {
            verifier.verifyGettersAndSetters().verifyToString();
        }
    }

}