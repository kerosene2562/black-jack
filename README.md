# Blackjack

A desktop Blackjack game implemented in Java using the **Swing** UI toolkit, featuring robust game logic built on **design patterns**, clean architecture principles, and multiple refactoring techniques.

![Image alt](https://github.com/kerosene2562/black-jack/raw/main/imgs/game.png)

---

## Project Overview

This is a complete Blackjack game with:

- A graphical user interface built using **Java Swing**
- Internal logic separated from UI via design patterns
- Game states like "Start", "Player Turn", "Dealer Turn", "End Round"
- Logger and advisor utility tools
- Modular code designed for maintainability and testability

---

## Design Patterns Used

### Singleton Pattern

**Where:** `StandardDeckCardManager`  
**Purpose:** Ensure only one instance of the card deck exists.

```java
public static StandardDeckCardManager getInstance() {
    if (instance == null) {
        instance = new StandardDeckCardManager();
    }
    return instance;
}
```

---

### Iterator Pattern

**Where:** `StandardDeckCardManager`  
**Purpose:** Sequential access to cards.

```java
@Override
public boolean hasNext() { return currentDeckPosition < MAXIMUM_CARD_COUNT; }

@Override
public PlayingCardRepresentation next() {
    return cardCollection.get(currentDeckPosition++);
}
```

---

### State Pattern

**Where:** `GameState` interface and its implementations (`GameStateStart`, `GameStatePlayerTurn`, etc.)

**Purpose:** Represent different phases of the game via classes, allowing transitions without conditionals.

```java
gameEngine.updateGameState(gameEngine.getDealerTurnState());
```

---

### Observer Pattern

**Where:** `BlackjackGameEngine` → `BlackjackGameWindow`  
**Purpose:** Decouple UI from game logic and notify observers when game state changes.

```java
setChanged();
notifyObservers("repaint");
```

---

### MVC/MV-variant Structure

Although not explicitly called MVC, the code respects **UI → Logic → Model** layering:

- `BlackjackGameWindow`, `ControlPanel`: View
- `BlackjackGameEngine`: Controller/Logic
- `StandardDeckCardManager`, `PlayingCardRepresentation`: Model

---

## Game Components

| Component | Responsibility |
|-----------|----------------|
| `BlackjackGameEngine` | Game flow, state transitions |
| `StandardDeckCardManager` | Deck creation, shuffling, iteration |
| `BlackjackParticipant` / `BlackjackDealer` | Hand management |
| `GameAdvisor` | Recommends Hit/Stay based on hand value |
| `GameLogger` | Logs game events to file |
| `GameState*` classes | Represent game states |
| `BlackjackGameWindow` | Assembles all Swing components |
| `PlayerActionControlPanel` | Buttons: Deal / Hit / Stay / Reset |

---

## Refactoring Techniques Used

### Replace Conditionals with Polymorphism

Handled by the `GameState` classes.

### Extract Class

Used to separate concerns: e.g., `GameAdvisor`, `GameLogger`, UI panels.

### Encapsulate Field

```java
private String cardSymbolCode;
public String getCardSymbolCode() { return cardSymbolCode; }
```

### Encapsulate Collection

```java
private ArrayList<PlayingCardRepresentation> handOfCards;
public ArrayList<PlayingCardRepresentation> getHandCards() { return handOfCards; }
```

### Extract Method

Game logic operations split into smaller methods for clarity.

---

## Software Design Principles Followed

### 1. Single Responsibility Principle (SRP)
Each class does one thing (UI, logic, card handling, etc.).

### 2. Open/Closed Principle (OCP)
New states can be added via new `GameState` classes.

### 3. Liskov Substitution Principle (LSP)
Dealer class can be used wherever Player class is expected.

### 4. Interface Segregation Principle (ISP)
Interfaces like `GameState` are minimal and focused.

### 5. Dependency Inversion Principle (DIP)
UI depends on abstractions (`Observer`), not concrete logic.

---

## Testing

Includes unit tests for:

- State transitions
- Deck integrity and Singleton
- Shuffling behavior

```java
@Test
void testInitialStateCorrect() {
    assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getStartState());
}
```

---

## How to Run

1. Compile all files:
```bash
javac blackjack/**/*.java
```

2. Run the game:
```bash
java blackjack.BlackjackLauncher
```

3. Make sure `assets/img/` contains card images:
- Example: `AS.png`, `KH.png`, `CardBack.png`

---

## Author

Developed by Romanets Oleksandr form IPZk-24-1 as a final design patterns project in Java.  
Includes practice with GUI (Swing), OOP principles, and clean code.

![Image alt](https://github.com/kerosene2562/black-jack/raw/main/imgs/strings.png)

---