Design Smells:
- many if statements
- high coupling -- feature envy

Refactoring choices:
- creating a new supermarket inteface and coles and woolies subclasses
-> utilising a template method for the supermarket checkout process as they follow the same steps/methods when
checking out but some methods may vary.
- moving some of the item coupling into the item class itself to handle