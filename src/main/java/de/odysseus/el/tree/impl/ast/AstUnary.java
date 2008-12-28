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

import javax.el.ELContext;
import javax.el.ELException;

import de.odysseus.el.misc.BooleanOperations;
import de.odysseus.el.misc.NumberOperations;
import de.odysseus.el.misc.TypeConverter;
import de.odysseus.el.tree.Bindings;

public class AstUnary extends AstRightValue {
	public interface Operator {
		public Object apply(TypeConverter converter, Object o);		
	}
	public static final Operator EMPTY = new Operator() {
		public Object apply(TypeConverter converter, Object o) { return BooleanOperations.empty(converter, o); }
		@Override public String toString() { return "empty"; }
	};
	public static final Operator NEG = new Operator() {
		public Object apply(TypeConverter converter, Object o) { return NumberOperations.neg(converter, o); }
		@Override public String toString() { return "-"; }
	};
	public static final Operator NOT = new Operator() {
		public Object apply(TypeConverter converter, Object o) { return BooleanOperations.not(converter, o); }
		@Override public String toString() { return "!"; }
	};

	private final Operator operator;
	private final AstNode child;

	public AstUnary(AstNode child, AstUnary.Operator operator) {
		this.child = child;
		this.operator = operator;
	}

	public Operator getOperator() {
		return operator;
	}

	@Override
	public Object eval(Bindings bindings, ELContext context) throws ELException {
		return operator.apply(bindings, child.eval(bindings, context));
	}

	@Override
	public String toString() {
		return "'" + operator.toString() + "'";
	}	

	@Override
	public void appendStructure(StringBuilder b, Bindings bindings) {
		b.append(operator);
		b.append(' ');
		child.appendStructure(b, bindings);
	}

	public int getCardinality() {
		return 1;
	}

	public AstNode getChild(int i) {
		return i == 0 ? child : null;
	}
}
