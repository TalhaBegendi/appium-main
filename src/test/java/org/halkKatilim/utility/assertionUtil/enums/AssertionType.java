package org.halkKatilim.utility.assertionUtil.enums;

public enum AssertionType {
    SINGLE {
        @Override
        public void execute(AssertionKey k, String... args) {
            k.getAssertion().assertElementExist(k.getElementKey());
        }
    },

    EXISTS {
        @Override
        public void execute(AssertionKey k, String... args) {
            k.getAssertion().assertElementsExists(k.getElementKey());
        }
    },

    NOT_EXISTS {
        @Override
        public void execute(AssertionKey k, String... args) {
            k.getAssertion().assertElementsNotExists(k.getElementKey());
        }
    },

    EQUAL_TEXT {
        @Override
        public void execute(AssertionKey k, String... args) {
            k.getAssertion().hardAssertEquals(args[0], args[1]);
        }
    },

    NOT_EQUAL_TEXT {
        @Override
        public void execute(AssertionKey k, String... args) {
            k.getAssertion().hardAssertNotEquals(args[0], args[1]);
        }
    };

    public abstract void execute(AssertionKey k, String... args);
}
