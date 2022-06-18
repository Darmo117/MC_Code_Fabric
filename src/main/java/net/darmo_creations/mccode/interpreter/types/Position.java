package net.darmo_creations.mccode.interpreter.types;

import net.darmo_creations.mccode.interpreter.Utils;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.Objects;

/**
 * Represents a position in the game world.
 * Positions may have relative coordinates, akin to the tilde notation of in-game commands.
 * <p>
 * Positions are comparable.
 */
public class Position implements Comparable<Position> {
  private final double x;
  private final double y;
  private final double z;
  private final Relativity xRelativity;
  private final Relativity yRelativity;
  private final Relativity zRelativity;

  /**
   * Create an absolute position.
   *
   * @param x X component.
   * @param y Y component.
   * @param z Z component.
   */
  public Position(final double x, final double y, final double z) {
    this(x, y, z, Relativity.ABSOLUTE, Relativity.ABSOLUTE, Relativity.ABSOLUTE);
  }

  /**
   * Create a position from another.
   *
   * @param other The position to copy.
   */
  public Position(final Position other) {
    this(other.getX(), other.getY(), other.getZ(),
        other.getXRelativity(), other.getYRelativity(), other.getZRelativity());
  }

  /**
   * Create an absolute position from a vector.
   *
   * @param vector The vector.
   */
  @SuppressWarnings("unused")
  public Position(final Vec3d vector) {
    this(vector.x, vector.y, vector.z);
  }

  /**
   * Create an absolute position from a vector.
   *
   * @param vector The vector.
   */
  public Position(final Vec3i vector) {
    this(vector.getX(), vector.getY(), vector.getZ());
  }

  /**
   * Create a relative position.
   *
   * @param x           X component.
   * @param y           Y component.
   * @param z           Z component.
   * @param xRelativity True if the X component is relative (tilde notation), false otherwise.
   * @param yRelativity True if the Y component is relative (tilde notation), false otherwise.
   * @param zRelativity True if the Z component is relative (tilde notation), false otherwise.
   */
  public Position(final double x, final double y, final double z,
                  final Relativity xRelativity, final Relativity yRelativity, final Relativity zRelativity) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.xRelativity = xRelativity;
    this.yRelativity = yRelativity;
    this.zRelativity = zRelativity;
  }

  /**
   * Create a relative position from another.
   *
   * @param other       The position to copy. Its relative flags are ignored.
   * @param xRelativity True if the X component is relative (tilde notation), false otherwise.
   * @param yRelativity True if the Y component is relative (tilde notation), false otherwise.
   * @param zRelativity True if the Z component is relative (tilde notation), false otherwise.
   */
  public Position(final Position other, final Relativity xRelativity, final Relativity yRelativity, final Relativity zRelativity) {
    this(other.getX(), other.getY(), other.getZ(), xRelativity, yRelativity, zRelativity);
  }

  /**
   * Create a relative position from another.
   *
   * @param vector      The vector.
   * @param xRelativity True if the X component is relative (tilde notation), false otherwise.
   * @param yRelativity True if the Y component is relative (tilde notation), false otherwise.
   * @param zRelativity True if the Z component is relative (tilde notation), false otherwise.
   */
  @SuppressWarnings("unused")
  public Position(final Vec3d vector, final Relativity xRelativity, final Relativity yRelativity, final Relativity zRelativity) {
    this(vector.x, vector.y, vector.z, xRelativity, yRelativity, zRelativity);
  }

  /**
   * Create a relative position from another.
   *
   * @param vector      The vector.
   * @param xRelativity True if the X component is relative (tilde notation), false otherwise.
   * @param yRelativity True if the Y component is relative (tilde notation), false otherwise.
   * @param zRelativity True if the Z component is relative (tilde notation), false otherwise.
   */
  @SuppressWarnings("unused")
  public Position(final Vec3i vector, final Relativity xRelativity, final Relativity yRelativity, final Relativity zRelativity) {
    this(vector.getX(), vector.getY(), vector.getZ(), xRelativity, yRelativity, zRelativity);
  }

  /**
   * Return the x compontent.
   */
  public double getX() {
    return this.x;
  }

  /**
   * Return the y compontent.
   */
  public double getY() {
    return this.y;
  }

  /**
   * Return the z compontent.
   */
  public double getZ() {
    return this.z;
  }

  /**
   * Return the command representation of the x coordinate.
   * Features the tilde/caret character if the component is relative.
   */
  public String getXCommandRepresentation() {
    return formatRelativePosition(this.getX(), this.xRelativity);
  }

  /**
   * Return the command representation of the y coordinate.
   * Features the tilde/caret character if the component is relative.
   */
  public String getYCommandRepresentation() {
    return formatRelativePosition(this.getY(), this.yRelativity);
  }

  /**
   * Return the command representation of the z coordinate.
   * Features the tilde/caret character if the component is relative.
   */
  public String getZCommandRepresentation() {
    return formatRelativePosition(this.getZ(), this.zRelativity);
  }

  private static String formatRelativePosition(final double v, final Relativity relativity) {
    // Each value is individually cast to string as otherwise the ternary would convert the long into a double
    String s = v == Math.floor(v) ? "" + (long) v : "" + v;
    if (relativity.isRelative()) {
      return relativity.getSymbol() + (v != 0 ? s : "");
    }
    return s;
  }

  /**
   * Return true if the x component is relative, false otherwise.
   */
  public boolean isXRelative() {
    return this.xRelativity.isRelative();
  }

  /**
   * Return true if the y component is relative, false otherwise.
   */
  public boolean isYRelative() {
    return this.yRelativity.isRelative();
  }

  /**
   * Return true if the z component is relative, false otherwise.
   */
  public boolean isZRelative() {
    return this.zRelativity.isRelative();
  }

  /**
   * Return the relativity of the x component.
   */
  public Relativity getXRelativity() {
    return this.xRelativity;
  }

  /**
   * Return the relativity of the y component.
   */
  public Relativity getYRelativity() {
    return this.yRelativity;
  }

  /**
   * Return the relativity of the z component.
   */
  public Relativity getZRelativity() {
    return this.zRelativity;
  }

  /**
   * Add a position to this position.
   *
   * @param x X component to add.
   * @param y Y component to add.
   * @param z Z component to add.
   * @return A new position.
   */
  public Position add(final double x, final double y, final double z) {
    return new Position(this.x + x, this.y + y, this.z + z,
        this.xRelativity, this.yRelativity, this.zRelativity);
  }

  /**
   * Add a position to this position.
   *
   * @param x X component to add.
   * @param y Y component to add.
   * @param z Z component to add.
   * @return A new position.
   */
  public Position add(final long x, final long y, final long z) {
    return this.add((double) x, y, z);
  }

  /**
   * Add a position to this position.
   *
   * @param other The position to add. Relative flags are ignored.
   * @return A new position.
   */
  public Position add(final Position other) {
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  /**
   * Add a position to this position.
   *
   * @param vector The vector to add.
   * @return A new position.
   */
  public Position add(final Vec3d vector) {
    return this.add(vector.x, vector.y, vector.z);
  }

  /**
   * Add a position to this position.
   *
   * @param vector The vector to add.
   * @return A new position.
   */
  public Position add(final Vec3i vector) {
    return this.add(vector.getX(), vector.getY(), vector.getZ());
  }

  /**
   * Subtract a position from this position.
   *
   * @param x X component to subtract.
   * @param y Y component to subtract.
   * @param z Z component to subtract.
   * @return A new position.
   */
  @SuppressWarnings("unused")
  public Position subtract(final double x, final double y, final double z) {
    return this.add(-x, -y, -z);
  }

  /**
   * Subtract a position from this position.
   *
   * @param x X component to subtract.
   * @param y Y component to subtract.
   * @param z Z component to subtract.
   * @return A new position.
   */
  @SuppressWarnings("unused")
  public Position subtract(final long x, final long y, final long z) {
    return this.add((double) -x, -y, -z);
  }

  /**
   * Subtract a position from this position.
   *
   * @param other The position to subtract. Relative flags are ignored.
   * @return A new position.
   */
  public Position subtract(final Position other) {
    return this.add(-other.getX(), -other.getY(), -other.getZ());
  }

  /**
   * Subtract a position from this position.
   *
   * @param vector The vector to subtract.
   * @return A new position.
   */
  @SuppressWarnings("unused")
  public Position subtract(final Vec3d vector) {
    return this.add(-vector.x, -vector.y, -vector.z);
  }

  /**
   * Subtract a position from this position.
   *
   * @param vector The vector to subtract.
   * @return A new position.
   */
  @SuppressWarnings("unused")
  public Position subtract(final Vec3i vector) {
    return this.add(-vector.getX(), -vector.getY(), -vector.getZ());
  }

  /**
   * Multiply each component of this position by the given value.
   *
   * @param n The multiplication coefficient.
   * @return A new position.
   */
  public Position multiply(final double n) {
    return new Position(this.x * n, this.y * n, this.z * n,
        this.xRelativity, this.yRelativity, this.zRelativity);
  }

  /**
   * Divide each component of this position by the given value.
   *
   * @param n The denominator.
   * @return A new position.
   */
  public Position divide(final double n) {
    return new Position(this.x / n, this.y / n, this.z / n,
        this.xRelativity, this.yRelativity, this.zRelativity);
  }

  /**
   * Perform an integer division on each component of this position with the given value.
   *
   * @param n The denominator.
   * @return A new position.
   */
  public Position intDivide(final double n) {
    return new Position((long) (this.x / n), (long) (this.y / n), (long) (this.z / n),
        this.xRelativity, this.yRelativity, this.zRelativity);
  }

  /**
   * Perform a modulo operation on each component of this position with the given value.
   *
   * @param n The denominator.
   * @return A new position.
   */
  public Position modulo(final double n) {
    return new Position(Utils.trueModulo(this.x, n), Utils.trueModulo(this.y, n), Utils.trueModulo(this.z, n),
        this.xRelativity, this.yRelativity, this.zRelativity);
  }

  /**
   * Raise each component of this position to the given exponent.
   *
   * @param n The exponent.
   * @return A new position.
   */
  public Position pow(final double n) {
    return new Position(Math.pow(this.x, n), Math.pow(this.y, n), Math.pow(this.z, n),
        this.xRelativity, this.yRelativity, this.zRelativity);
  }

  /**
   * Return a position that is n blocks up.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position up(final long n) {
    return this.offset(Direction.UP, n);
  }

  /**
   * Return a position that is n blocks down.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position down(final long n) {
    return this.offset(Direction.DOWN, n);
  }

  /**
   * Return a position that is n blocks to the north.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position north(final long n) {
    return this.offset(Direction.NORTH, n);
  }

  /**
   * Return a position that is n blocks to the south.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position south(final long n) {
    return this.offset(Direction.SOUTH, n);
  }

  /**
   * Return a position that is n blocks to the west.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position west(final long n) {
    return this.offset(Direction.WEST, n);
  }

  /**
   * Return a position that is n blocks to the east.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position east(final long n) {
    return this.offset(Direction.EAST, n);
  }

  /**
   * Return a position that is offset by n blocks in the given direction.
   *
   * @param n The offset.
   * @return A new position.
   */
  public Position offset(final Direction facing, final long n) {
    return new Position(
        this.x + facing.getOffsetX() * n,
        this.y + facing.getOffsetY() * n,
        this.z + facing.getOffsetZ() * n,
        this.xRelativity, this.yRelativity, this.zRelativity
    );
  }

  /**
   * Rotate this position by the given amount.
   *
   * @param rotation Rotation angle.
   * @return A new position.
   */
  public Position rotate(final BlockRotation rotation) {
    return switch (rotation) {
      case NONE -> new Position(this);
      case CLOCKWISE_90 -> new Position(-this.z, this.y, this.x, this.zRelativity, this.yRelativity, this.xRelativity);
      case CLOCKWISE_180 -> new Position(-this.x, this.y, -this.z);
      case COUNTERCLOCKWISE_90 ->
          new Position(this.z, this.y, -this.x, this.zRelativity, this.yRelativity, this.xRelativity);
    };
  }

  /**
   * Return the euclidian distance between this position and the specified one.
   *
   * @param other A position
   * @return The euclidian distance.
   */
  public double getDistance(final Position other) {
    return Math.sqrt(this.getDistanceSq(other));
  }

  /**
   * Return the squared euclidian distance between this position and the specified one.
   *
   * @param other A position
   * @return The squared euclidian distance.
   */
  public double getDistanceSq(final Position other) {
    double d0 = other.getX() - this.getX();
    double d1 = other.getY() - this.getY();
    double d2 = other.getZ() - this.getZ();
    return d0 * d0 + d1 * d1 + d2 * d2;
  }

  /**
   * Convert this position to a {@link BlockPos} instance.
   *
   * @return A {@link BlockPos} object.
   * @throws IllegalStateException If this position has at least one relative component.
   */
  public BlockPos toBlockPos() {
    if (this.isXRelative() || this.isYRelative() || this.isZRelative()) {
      throw new IllegalStateException("cannot convert relative positon to block position");
    }
    return new BlockPos(this.x, this.y, this.z);
  }

  /**
   * Convert this position to a {@link Vec3d} instance.
   *
   * @return A {@link Vec3d} object.
   */
  @SuppressWarnings("unused")
  public Vec3d toVec3d() {
    return new Vec3d(this.x, this.y, this.z);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    Position that = (Position) o;
    return Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0 && Double.compare(that.z, this.z) == 0
        && this.xRelativity == that.xRelativity && this.yRelativity == that.yRelativity && this.zRelativity == that.zRelativity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y, this.z, this.xRelativity, this.yRelativity, this.zRelativity);
  }

  @Override
  public int compareTo(Position o) {
    if (this.getY() == o.getY()) {
      return this.getZ() == o.getZ()
          ? Double.compare(this.getX(), o.getX())
          : Double.compare(this.getZ(), o.getZ());
    } else {
      return Double.compare(this.getY(), o.getY());
    }
  }

  @Override
  public String toString() {
    return "%s %s %s".formatted(this.getXCommandRepresentation(), this.getYCommandRepresentation(), this.getZCommandRepresentation());
  }

  public enum Relativity {
    ABSOLUTE(""), TILDE("~"), CARET("^");

    private final String symbol;

    Relativity(final String symbol) {
      this.symbol = symbol;
    }

    public boolean isRelative() {
      return this != ABSOLUTE;
    }

    public String getSymbol() {
      return this.symbol;
    }

    @Override
    public String toString() {
      return this.symbol;
    }

    /**
     * Return the relativity object that corresponds to the given string.
     */
    public static Relativity fromString(final String s) {
      if (s == null) {
        return ABSOLUTE;
      }
      return switch (s) {
        case "~" -> TILDE;
        case "^" -> CARET;
        default -> ABSOLUTE;
      };
    }
  }
}
