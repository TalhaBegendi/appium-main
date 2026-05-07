package org.halkKatilim.utility.assertionUtil.enums;
import org.halkKatilim.utility.context.ExecutionContext;

public enum AssertionType {
    SINGLE {
        @Override
        public void execute(AssertionKey k, String... args) {
            ExecutionContext.getAssertion().assertElementExist(k.getElementKey());
        }
    },

    EXISTS {
        @Override
        public void execute(AssertionKey k, String... args) {
            ExecutionContext.getAssertion().assertElementsExists(k.getElementKey());
        }
    },

    NOT_EXISTS {
        @Override
        public void execute(AssertionKey k, String... args) {
            ExecutionContext.getAssertion().assertElementsNotExists(k.getElementKey());
        }
    },

    EQUAL_TEXT {
        @Override
        public void execute(AssertionKey k, String... args) {
            ExecutionContext.getAssertion().hardAssertEquals(args[0], args[1]);
        }
    },

    NOT_EQUAL_TEXT {
        @Override
        public void execute(AssertionKey k, String... args) {
            ExecutionContext.getAssertion().hardAssertNotEquals(args[0], args[1]);
        }
    };

    public abstract void execute(AssertionKey k, String... args);
}
