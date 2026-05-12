package net.foxboi.summon.api.model;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Element {
    private double
            fromX = 0,
            fromY = 0,
            fromZ = 0;

    private double
            toX = 16,
            toY = 16,
            toZ = 16;

    private double
            rotationX = 0,
            rotationY = 0,
            rotationZ = 0;

    private double
            originX = 8,
            originY = 8,
            originZ = 8;

    private boolean rescale = false;
    private boolean shade = true;
    private int lightEmission = 0;

    private Face
            north,
            east,
            south,
            west,
            up,
            down;

    private Element() {
    }

    private Element(Element copy) {
        this.fromX = copy.fromX;
        this.fromY = copy.fromY;
        this.fromZ = copy.fromZ;
        this.toX = copy.toX;
        this.toY = copy.toY;
        this.toZ = copy.toZ;
        this.rotationX = copy.rotationX;
        this.rotationY = copy.rotationY;
        this.rotationZ = copy.rotationZ;
        this.originX = copy.originX;
        this.originY = copy.originY;
        this.originZ = copy.originZ;
        this.rescale = copy.rescale;
        this.shade = copy.shade;
        this.lightEmission = copy.lightEmission;
        this.north = copyFace(copy.north);
        this.east = copyFace(copy.east);
        this.south = copyFace(copy.south);
        this.west = copyFace(copy.west);
        this.up = copyFace(copy.up);
        this.down = copyFace(copy.down);
    }

    private static Face copyFace(Face o) {
        return o == null ? null : o.copy();
    }

    public Element copy() {
        return new Element(this);
    }

    public double fromX() {
        return fromX;
    }

    public double fromY() {
        return fromY;
    }

    public double fromZ() {
        return fromZ;
    }

    public double toX() {
        return toX;
    }

    public double toY() {
        return toY;
    }

    public double toZ() {
        return toZ;
    }

    public double rotationX() {
        return rotationX;
    }

    public double rotationY() {
        return rotationY;
    }

    public double rotationZ() {
        return rotationZ;
    }

    public double originX() {
        return originX;
    }

    public double originY() {
        return originY;
    }

    public double originZ() {
        return originZ;
    }

    public boolean rescale() {
        return rescale;
    }

    public boolean shade() {
        return shade;
    }

    public int lightEmission() {
        return lightEmission;
    }

    public Face north() {
        return north;
    }

    public Face east() {
        return east;
    }

    public Face south() {
        return south;
    }

    public Face west() {
        return west;
    }

    public Face up() {
        return up;
    }

    public Face down() {
        return down;
    }

    public Face face(Direction dir) {
        return switch (dir) {
            case DOWN -> down();
            case UP -> up();
            case NORTH -> north();
            case SOUTH -> south();
            case WEST -> west();
            case EAST -> east();
        };
    }

    public Element from(double x, double y, double z) {
        fromX = x;
        fromY = y;
        fromZ = z;
        return this;
    }

    public Element to(double x, double y, double z) {
        toX = x;
        toY = y;
        toZ = z;
        return this;
    }

    public Element rotation(double x, double y, double z) {
        rotationX = x;
        rotationY = y;
        rotationZ = z;
        return this;
    }

    public Element rotation(Direction.Axis axis, double angle) {
        rotationX = axis == Direction.Axis.X ? angle : 0;
        rotationY = axis == Direction.Axis.Y ? angle : 0;
        rotationZ = axis == Direction.Axis.Z ? angle : 0;
        return this;
    }

    public Element origin(double x, double y, double z) {
        originX = x;
        originY = y;
        originZ = z;
        return this;
    }

    public Element rescale(boolean rescale) {
        this.rescale = rescale;
        return this;
    }

    public Element shade(boolean shade) {
        this.shade = shade;
        return this;
    }

    public Element lightEmission(int lightEmission) {
        this.lightEmission = Mth.clamp(lightEmission, 0, 15);
        return this;
    }

    public Element north(Face face) {
        north = face;
        return this;
    }

    public Element east(Face face) {
        east = face;
        return this;
    }

    public Element south(Face face) {
        south = face;
        return this;
    }

    public Element west(Face face) {
        west = face;
        return this;
    }

    public Element up(Face face) {
        up = face;
        return this;
    }

    public Element down(Face face) {
        down = face;
        return this;
    }

    public Element north(UnaryOperator<Face> face) {
        north = face.apply(north == null ? Face.face() : north);
        return this;
    }

    public Element east(UnaryOperator<Face> face) {
        east = face.apply(east == null ? Face.face() : east);
        return this;
    }

    public Element south(UnaryOperator<Face> face) {
        south = face.apply(south == null ? Face.face() : south);
        return this;
    }

    public Element west(UnaryOperator<Face> face) {
        west = face.apply(west == null ? Face.face() : west);
        return this;
    }

    public Element up(UnaryOperator<Face> face) {
        up = face.apply(up == null ? Face.face() : up);
        return this;
    }

    public Element down(UnaryOperator<Face> face) {
        down = face.apply(down == null ? Face.face() : down);
        return this;
    }

    public Element face(Direction dir, Face face) {
        return switch (dir) {
            case DOWN -> down(face);
            case UP -> up(face);
            case NORTH -> north(face);
            case SOUTH -> south(face);
            case WEST -> west(face);
            case EAST -> east(face);
        };
    }

    public Element face(Direction dir, UnaryOperator<Face> face) {
        return switch (dir) {
            case DOWN -> down(face);
            case UP -> up(face);
            case NORTH -> north(face);
            case SOUTH -> south(face);
            case WEST -> west(face);
            case EAST -> east(face);
        };
    }

    public Element allFaces(Face face) {
        down(face);
        up(face);
        north(face);
        south(face);
        west(face);
        east(face);
        return this;
    }

    public Element allFaces(UnaryOperator<Face> face) {
        down(face);
        up(face);
        north(face);
        south(face);
        west(face);
        east(face);
        return this;
    }

    public Element existingFaces(Face face) {
        if (down != null) {
            down(face);
        }
        if (up != null) {
            up(face);
        }
        if (north != null) {
            north(face);
        }
        if (south != null) {
            south(face);
        }
        if (west != null) {
            west(face);
        }
        if (east != null) {
            east(face);
        }
        return this;
    }

    public Element existingFaces(UnaryOperator<Face> face) {
        if (down != null) {
            down(face);
        }
        if (up != null) {
            up(face);
        }
        if (north != null) {
            north(face);
        }
        if (south != null) {
            south(face);
        }
        if (west != null) {
            west(face);
        }
        if (east != null) {
            east(face);
        }
        return this;
    }

    public static Element element() {
        return new Element();
    }

    public static DataResult<Element> parse(JsonElement json) {
        return CODEC.parse(JsonOps.INSTANCE, json);
    }

    public static Element parseOrThrow(JsonElement json) {
        return parse(json).getOrThrow();
    }

    public JsonElement toJson() {
        return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow();
    }

    private List<Double> fromForCodec() {
        return List.of(fromX, fromY, fromZ);
    }

    private List<Double> toForCodec() {
        return List.of(toX, toY, toZ);
    }

    private List<Double> originForCodec() {
        return List.of(originX, originY, originZ);
    }

    private Optional<Either<AxisAngleRotation, EulerRotation>> rotationForCodec() {
        var zeroX = rotationX == 0;
        var zeroY = rotationY == 0;
        var zeroZ = rotationZ == 0;

        if (zeroX && zeroY && zeroZ) {
            return Optional.empty();
        }

        if (zeroX && zeroY) {
            return Optional.of(Either.left(
                    new AxisAngleRotation(Direction.Axis.Z, rotationZ, originForCodec(), rescale)
            ));
        }

        if (zeroX && zeroZ) {
            return Optional.of(Either.left(
                    new AxisAngleRotation(Direction.Axis.Y, rotationY, originForCodec(), rescale)
            ));
        }

        if (zeroY && zeroZ) {
            return Optional.of(Either.left(
                    new AxisAngleRotation(Direction.Axis.X, rotationX, originForCodec(), rescale)
            ));
        }

        return Optional.of(Either.right(
                new EulerRotation(rotationX, rotationY, rotationZ, originForCodec(), rescale)
        ));
    }

    private Faces facesForCodec() {
        return new Faces(
                Optional.ofNullable(north),
                Optional.ofNullable(east),
                Optional.ofNullable(south),
                Optional.ofNullable(west),
                Optional.ofNullable(up),
                Optional.ofNullable(down)
        );
    }

    private Element originFromCodec(List<Double> origin) {
        return origin(origin.get(0), origin.get(1), origin.get(2));
    }

    private Element rotationFromCodec(Optional<Either<AxisAngleRotation, EulerRotation>> rotation) {
        rotation.ifPresent(either -> {
            either.ifRight(euler -> {
                rotation(euler.x, euler.y, euler.z);
                originFromCodec(euler.origin);
                rescale(euler.rescale);
            });

            either.ifLeft(axisAngle -> {
                rotation(axisAngle.axis, axisAngle.angle);
                originFromCodec(axisAngle.origin);
                rescale(axisAngle.rescale);
            });
        });

        return this;
    }

    private Element facesFromCodec(Faces faces) {
        faces.north.ifPresent(this::north);
        faces.east.ifPresent(this::east);
        faces.south.ifPresent(this::south);
        faces.west.ifPresent(this::west);
        faces.up.ifPresent(this::up);
        faces.down.ifPresent(this::down);
        return this;
    }

    private static Element fromCodec(List<Double> from, List<Double> to, Optional<Either<AxisAngleRotation, EulerRotation>> rotation, boolean shade, int lightEmission, Faces faces) {
        return new Element()
                .from(from.get(0), from.get(1), from.get(2))
                .to(to.get(0), to.get(1), to.get(2))
                .rotationFromCodec(rotation)
                .shade(shade)
                .lightEmission(lightEmission)
                .facesFromCodec(faces);
    }

    private record EulerRotation(double x, double y, double z, List<Double> origin, boolean rescale) {
    }

    private record AxisAngleRotation(Direction.Axis axis, double angle, List<Double> origin, boolean rescale) {
    }

    private record Faces(Optional<Face> north, Optional<Face> east, Optional<Face> south, Optional<Face> west, Optional<Face> up, Optional<Face> down) {
    }

    private static Codec<Element> createCodec() {
        Codec<List<Double>> coords = Codec.DOUBLE.listOf(3, 3);

        Codec<EulerRotation> eulerRot = RecordCodecBuilder.create(i -> i.group(
                Codec.DOUBLE.fieldOf("x").forGetter(EulerRotation::x),
                Codec.DOUBLE.fieldOf("y").forGetter(EulerRotation::y),
                Codec.DOUBLE.fieldOf("z").forGetter(EulerRotation::z),
                coords.fieldOf("origin").forGetter(EulerRotation::origin),
                Codec.BOOL.fieldOf("rescale").forGetter(EulerRotation::rescale)
        ).apply(i, EulerRotation::new));

        Codec<AxisAngleRotation> axisAngleRot = RecordCodecBuilder.create(i -> i.group(
                Direction.Axis.CODEC.fieldOf("axis").forGetter(AxisAngleRotation::axis),
                Codec.DOUBLE.fieldOf("angle").forGetter(AxisAngleRotation::angle),
                coords.fieldOf("origin").forGetter(AxisAngleRotation::origin),
                Codec.BOOL.fieldOf("rescale").forGetter(AxisAngleRotation::rescale)
        ).apply(i, AxisAngleRotation::new));

        Codec<Either<AxisAngleRotation, EulerRotation>> rotation = Codec.either(axisAngleRot, eulerRot);

        Codec<Faces> faces = RecordCodecBuilder.create(i -> i.group(
                Face.CODEC.optionalFieldOf("north").forGetter(Faces::north),
                Face.CODEC.optionalFieldOf("east").forGetter(Faces::east),
                Face.CODEC.optionalFieldOf("south").forGetter(Faces::south),
                Face.CODEC.optionalFieldOf("west").forGetter(Faces::west),
                Face.CODEC.optionalFieldOf("up").forGetter(Faces::up),
                Face.CODEC.optionalFieldOf("down").forGetter(Faces::down)
        ).apply(i, Faces::new));

        return RecordCodecBuilder.create(i -> i.group(
                coords.fieldOf("from").forGetter(Element::fromForCodec),
                coords.fieldOf("to").forGetter(Element::toForCodec),
                rotation.optionalFieldOf("rotation").forGetter(Element::rotationForCodec),
                Codec.BOOL.fieldOf("shade").forGetter(Element::shade),
                Codec.intRange(0, 15).fieldOf("light_emission").forGetter(Element::lightEmission),
                faces.fieldOf("faces").forGetter(Element::facesForCodec)
        ).apply(i, Element::fromCodec));
    }

    public static final Codec<Element> CODEC = createCodec();
}
