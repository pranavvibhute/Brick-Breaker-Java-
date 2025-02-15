package src;

public enum BrickType {
    NORMAL,      // 🟧 Breaks in one hit
    STRONG,      // 🟦 Needs two hits to break
    EXPLODING,   // 🟥 Destroys nearby bricks when hit
    MOVING       // 🟩 Moves left & right slowly
}
