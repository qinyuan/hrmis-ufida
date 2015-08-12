package qinyuan.lib.office;

public enum ExcelColor {
	RED, GREEN, WHITE, BLACK, BLUE, YELLOW;

	static short getColorIndex(ExcelColor color) {
		switch (color) {
		case BLACK:
			return 0;
		case WHITE:
			return 1;
		case RED:
			return 2;
		case GREEN:
			return 3;
		case BLUE:
			return 4;
		case YELLOW:
			return 5;
		default:
			return 0;
		}
	}
}
