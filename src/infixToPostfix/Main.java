package infixToPostfix;

import java.util.*;

public class Main {
	public static void main(String[] args) {
		System.out.println("INFIX TO POSTFIX EVALUATOR\n**************************");
		String infix = "2*3+7/3";
		System.out.println("\nOriginal infix expression: " + infix);
		System.out.println("\nPostfix conversion: " + toPostfix(infix));
		System.out.println("\nPostfix expression evaluation: " + evalPostfix(toPostfix(infix)));
	}

	/**
	 * Evaluates the result of a given postfix expression
	 * @param postfix expression
	 * @return an integer result of the postfix expression evaluation
	 */
	public static int evalPostfix(String postfix) {
		Stack<Integer> stack = new Stack<Integer>();
		int i = 0;
		while (i < postfix.length()) {
			String currentChar = postfix.substring(i, i + 1);
			if (isOperand(currentChar)) {
				stack.push(Integer.valueOf(currentChar));
				i++;
			} else if (isOperator(currentChar)) {
				int operand1 = stack.pop();
				int operand2 = stack.pop();
				stack.push(evalIntegerOperation(operand1, operand2, currentChar));
				i++;
			}
		}
		return Integer.valueOf(stack.pop());
	}

	/**
	 * Helper method to process postfix expression. Processes simple arithmetic and stores in an Integer object
	 * @param operand1 an integer representing the first operand
	 * @param operand2 an integer representing the second operand
	 * @param operator a String representing the operator
	 * @return an Integer object representing the result of the operation
	 */
	public static Integer evalIntegerOperation(int operand1, int operand2, String operator) {
		if (operator.equals("+")) {
			Integer integer = Integer.valueOf(operand1 + operand2);
			return integer;
		} else if (operator.equals("-")) {
			Integer integer = Integer.valueOf(operand1 - operand2);
			return integer;
		} else if (operator.equals("*")) {
			Integer integer = Integer.valueOf(operand1 * operand2);
			return integer;
		} else if (operator.equals("/")) {
			Integer integer = Integer.valueOf(operand1 / operand2);
			return integer;
		}
		return 0;
	}

	/**
	 * Method that converts an infix expression string to a postfix expression string 
	 * @param eq String representing and infix expression
	 * @return a String representing the postfix version of the original infix String
	 */
	public static String toPostfix(String eq) {
		Stack<String> stack = new Stack<String>();
		String postfix = "";
		int i = 0;
		while (i < eq.length()) {
			String currentChar = eq.substring(i, i + 1);
			if (isOperand(currentChar)) {
				postfix += currentChar;
				i++;
			} else if (isOperator(currentChar)) {
				while (!stack.empty() && hasPriorityOperator(stack, currentChar)) {
					postfix += stack.pop();
				}
				stack.push(currentChar);
				i++;
			} else if (currentChar.equals("(")) {
				stack.push(currentChar);
				i++;
			} else if (currentChar.equals(")")) {
				while (!stack.peek().equals("(")) {
					postfix += stack.pop();
				}
				stack.pop();
				i++;
			}
		}
		while (!stack.empty()) {
			postfix += stack.pop();
		}
		return postfix;
	}

	/**
	 * Helper method to determine if a given character represents an operand
	 * @param c String of a single character to be processed
	 * @return true if c is an operand (a number from 0-9), false otherwise
	 */
	public static boolean isOperand(String c) {
		if (c.equals("0") || c.equals("1") || c.equals("2") || c.equals("3") || c.equals("4") || c.equals("5")
				|| c.equals("6") || c.equals("7") || c.equals("8") || c.equals("9")) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to determine if a given character represents an operator
	 * @param c String of a single character to be processed
	 * @return true if c is an operator (either +, -, *, or /), false otherwise
	 */
	public static boolean isOperator(String c) {
		if (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/")) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to convert infix to postfix. Determines if operator at the top of the stack takes precedence
	 * @param stack containing operators
	 * @param c current operator to be compared
	 * @return true if current operator has priority over the operator at the top of the stack, false otherwise
	 */
	public static boolean hasPriorityOperator(Stack<String> stack, String c) {
		String topOfStack = stack.peek();
		if ((c.equals("+") || c.equals("-")) && (topOfStack.equals("*") || topOfStack.equals("/")))
			return true;
		else if ((c.equals("*") || c.equals("/")) && (topOfStack.equals("*") || topOfStack.equals("/")))
			return true;
		else if ((c.equals("+") || c.equals("-")) && (topOfStack.equals("*") || topOfStack.equals("/")))
			return false;
		else if ((c.equals("+") || c.equals("-")) && (topOfStack.equals("+") || topOfStack.equals("-")))
			return true;
		return false;
	}
}
