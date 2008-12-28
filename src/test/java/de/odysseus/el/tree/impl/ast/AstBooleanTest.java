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
package de.odysseus.el.tree.impl.ast;

import javax.el.ELException;

import de.odysseus.el.TestCase;
import de.odysseus.el.tree.Bindings;

public class AstBooleanTest extends TestCase {
	private Bindings bindings = new Bindings(null, null, null);
	
	AstBoolean parseNode(String expression) {
		return (AstBoolean)parse(expression).getRoot().getChild(0);
	}

	public void testEval() {
		assertEquals(true, parseNode("${true}").eval(bindings, null));
		assertEquals(false, parseNode("${false}").eval(bindings, null));
	}

	public void testAppendStructure() {
		StringBuilder s = new StringBuilder();
		parseNode("${true}").appendStructure(s, bindings);
		parseNode("${false}").appendStructure(s, bindings);
		assertEquals("truefalse", s.toString());
	}

	public void testIsLiteralText() {
		assertFalse(parseNode("${true}").isLiteralText());
	}

	public void testIsLeftValue() {
		assertFalse(parseNode("${true}").isLeftValue());
	}

	public void testGetType() {
		assertNull(parseNode("${true}").getType(bindings, null));
	}

	public void testIsReadOnly() {
		assertTrue(parseNode("${true}").isReadOnly(bindings, null));
	}

	public void testSetValue() {
		try { parseNode("${true}").setValue(bindings, null, null); fail(); } catch (ELException e) {}
	}

	public void testGetValue() {
		assertEquals(true, parseNode("${true}").getValue(bindings, null, null));
		assertEquals("true", parseNode("${true}").getValue(bindings, null, String.class));
	}
}
