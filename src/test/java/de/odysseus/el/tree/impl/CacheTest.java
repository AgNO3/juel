/*
 * Copyright 2006, 2007 Odysseus Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package de.odysseus.el.tree.impl;

import java.util.HashMap;
import java.util.Map;

import de.odysseus.el.TestCase;
import de.odysseus.el.tree.Tree;

public class CacheTest extends TestCase {
	public void testPrimary() {
		Cache cache = null;

		// check if non-caching mode works
		cache = new Cache(0, null);
		cache.put("1", parse("1"));
		assertNull(cache.get("1"));

		// check if caching works, cache size 1
		cache = new Cache(1, null);
		cache.put("1", parse("1"));
		assertNotNull(cache.get("1"));

		// check if removeEldest works, cache size 1
		cache = new Cache(1, null);
		cache.put("1", parse("1"));
		cache.put("2", parse("2"));
		assertNull(cache.get("1"));

		// check if removeEldest works, cache size 9
		cache = new Cache(9, null);
		for (int i = 1; i < 10; i++) {
			cache.put("" + i, parse("" + i));
			for (int j = 1; j <= i; j++) {
				assertNotNull(cache.get("" + j));
			}
		}
		cache.put("10", parse("10"));
		assertNull(cache.get("1"));
		for (int j = 2; j <= 10; j++) {
			assertNotNull(cache.get("" + j));
		}
	}

	public void testSecondary() {
		Map<String,Tree> map = new HashMap<String,Tree>();
		Cache cache = new Cache(1, map);
		
		// check secondary cache
		cache.put("1", parse("1"));
		cache.put("2", parse("2"));
		assertNotNull(cache.get("1"));
		assertNotNull(cache.get("2"));
		assertTrue(map.containsKey("1"));
		assertFalse(map.containsKey("2"));
		map.remove("1");
		assertNull(cache.get("1"));
	}
}
