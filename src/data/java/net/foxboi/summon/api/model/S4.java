package net.foxboi.summon.api.model;

/**
 * Permutational symmetry group S4. S4 is the group of all permutations of a sequence of four distinct elements. It
 * contains <i>4! = 24</i> elements. S4 is also the group of all rotational symmetries of a cube. It is therefore useful
 * in computing composed model rotations.
 */
public enum S4 {
    // Permutational symmetry (S) group of four (4) elements, i.e. S4.
    // It maps all the 24 distinct ways in which 4 distinct elements can be
    // arranged in a row. It just so happens to be that all the ways in which
    // a cube can be rotated to stay axis-aligned is the same exact group.

    // The reason we use a group here is that we can, with some fairly simple
    // computation, find the composite of two rotations, in whatever order we
    // like. In fact, since the group is finite, we can just set up a lookup
    // table (called a Cayley table in group theory). Nevertheless, we simply
    // just compute each composition by permuting a 4-element list and
    // finding the corresponding enum entry. It can be done in constant time
    // given how we sorted the enum entries, see below.

    P0123(0, 1, 2, 3),
    P0132(0, 1, 3, 2),
    P0213(0, 2, 1, 3),
    P0231(0, 2, 3, 1),
    P0312(0, 3, 1, 2),
    P0321(0, 3, 2, 1),

    P1023(1, 0, 2, 3),
    P1032(1, 0, 3, 2),
    P1203(1, 2, 0, 3),
    P1230(1, 2, 3, 0),
    P1302(1, 3, 0, 2),
    P1320(1, 3, 2, 0),

    P2013(2, 0, 1, 3),
    P2031(2, 0, 3, 1),
    P2103(2, 1, 0, 3),
    P2130(2, 1, 3, 0),
    P2301(2, 3, 0, 1),
    P2310(2, 3, 1, 0),

    P3012(3, 0, 1, 2),
    P3021(3, 0, 2, 1),
    P3102(3, 1, 0, 2),
    P3120(3, 1, 2, 0),
    P3201(3, 2, 0, 1),
    P3210(3, 2, 1, 0);


    private static final S4[] VALUES = values();

    // Identity represents the unpermuted sequence: 0, 1, 2, 3. It is equivalent
    // to not rotating the cube and is inherently part of the group.

    public static final S4 IDENTITY = P0123;

    // Any of the elements of the group now represents some rotation, and they form
    // certain cycles. For example, there is an element that represents the reverse
    // permutation, 3, 2, 1, 0. Each element, when repeatedly composed with itself,
    // will eventually lead to itself, that is, it creates a cycle.

    // The identity element creates a cycle of 1, it composes with itself into itself
    // immediately, and is the only one of that kind. Some elements create cycles of
    // two, and some create cycles of three. None of these are particularly
    // interesting in and on itself.

    // Then there are 6 elements that create cycles of 4; these are the ones we are
    // interested in, sicne these all correspond to 90-degree rotations of a cube
    // around some respective axis, including both clockwise and counterclockwise.
    // Put differently, each belongs to one of the faces of the cube, such that that
    // the cube appears to rotate 90 degrees clockwise when observed from that side.

    // We can derive how these permutations map by taking one of the faces of the
    // cube that faces in a positive axis direction (e.g. east, up or south, we
    // took east) and numbering the corners 0123 in clockwise order. On the opposite
    // side of the cube we number the corners in the same way, but such that each of
    // the four diagonals of the cube connects two corners with the same number.

    // We then look back at the same face we took initially and how a rotation around
    // each axis would permute the corner numbers we see in front. We want to work
    // with clockwise rotations and start on the east face, so a rotation around the
    // X axis would perfectly rotate the numbers around.

    /*
          0 > 1      3   0      2   3      1   2
          ^   v  ->         ->         ->
          3 < 2      2   1      1   0      0   3
     */
    private static final S4[] X_ROTATIONS = {
            P0123,
            P3012,
            P2301,
            P1230,
    };

    // An Y axis rotation will make the numbers on the right shift
    // to the left. The numbers on the left disappear, but following
    // the diagonals, they appear swapped on the right.

    /*
          0 < 1      1   3      3   2      2   0
            x    ->         ->         ->
          3 < 2      2   0      0   1      1   3
     */
    private static final S4[] Y_ROTATIONS = {
            P0123,
            P1302,
            P3210,
            P2031,
    };

    // A Z axis rotation will make the numbers on the bottom shift
    // down, and in a similar fashion to an Y rotation, the numbers
    // on the bottom will appear swapped on the top.

    /*
          0   1      2   3      1   0      3   2
          v x v  ->         ->         ->
          3   2      0   1      2   3      1   0
     */
    private static final S4[] Z_ROTATIONS = {
            P0123,
            P2310,
            P1032,
            P3201,
    };

    static {
        // Compute the euler angles. We simply do this by going over all the 64 rotations, computing
        // which group element belongs to each, and assigning the angles we found to the group elements.

        for (int x = 0; x < 360; x += 90) {
            for (int y = 0; y < 360; y += 90) {
                for (int z = 0; z < 360; z += 90) {
                    var g = IDENTITY.x(x).y(y).z(z);
                    var a = g.eulerAngles;

                    // There are 64 combinations of angles, and only 24 possible rotations, so
                    // there must be some tuples of angles that lead to the same rotation. In
                    // such cases, we choose the one with the lowest z rotation. This leaves us
                    // with 16 rotations that need no z rotation and 8 rotations that need a 90
                    // degree z rotation. We can thus strip out the z angle in two thirds of the
                    // cases.
                    if (z < a[2]) {
                        a[0] = x;
                        a[1] = y;
                        a[2] = z;
                    }
                }
            }
        }
    }

    private final int[] permutation;
    private final int[] eulerAngles;

    S4(int i0, int i1, int i2, int i3) {
        permutation = new int[] { i0, i1, i2, i3 };
        eulerAngles = new int[] { 0, 0, 360 }; // We'll compute these in a bit
    }

    /**
     * Given all rotations apply in XYZ order, returns the clockwise X rotation in degrees.
     */
    public int x() {
        return eulerAngles[0];
    }

    /**
     * Given all rotations apply in XYZ order, returns the clockwise Y rotation in degrees.
     */
    public int y() {
        return eulerAngles[1];
    }

    /**
     * Given all rotations apply in XYZ order, returns the clockwise Z rotation in degrees.
     */
    public int z() {
        return eulerAngles[2];
    }

    /**
     * Apply a clockwise rotation around the X axis. If the angle is not in increments of 90 degrees, it will be rounded
     * down towards zero. Returns the new group element.
     */
    public S4 x(int angle) {
        return X_ROTATIONS[(angle / 90) & 3].compose(this);
    }

    /**
     * Apply a clockwise rotation around the Y axis. If the angle is not in increments of 90 degrees, it will be rounded
     * down towards zero. Returns the new group element.
     */
    public S4 y(int angle) {
        return Y_ROTATIONS[(angle / 90) & 3].compose(this);
    }

    /**
     * Apply a clockwise rotation around the Z axis. If the angle is not in increments of 90 degrees, it will be rounded
     * down towards zero. Returns the new group element.
     */
    public S4 z(int angle) {
        return Z_ROTATIONS[(angle / 90) & 3].compose(this);
    }

    /**
     * Composes this group element with another. In rotational terms, this is equivalent to applying this element
     * <strong>after</strong> the given group element.
     */
    public S4 compose(S4 rhs) {
        var p = permutation;
        var q = rhs.permutation;

        // Compute the composed permutation, we do this by taking the first 3
        // permutation values of p and using those as indices for q. This gives
        // us the first 3 permutation values of the composed element. We only
        // need the first 3 elements, since the fourth can be implied from the
        // first 3.

        // Do note that we still need the permutation arrays to have 4 elements,
        // since the indices go up to 3.

        return ofPermutation(q[p[0]], q[p[1]], q[p[2]]);
    }

    private static S4 ofPermutation(int i0, int i1, int i2) {
        // Deduce the enum ordinal from the permutation:

        // - The first element can be any of the 4 options
        //   -> This is one of {0, 1, 2, 3}
        //   -> The values of the enum are grouped first by this element

        // - The second element can be any of the remaining 3 options
        //   -> We normalise this to {0, 1, 2} by subtracting the first element
        //      if it is smaller than the second
        //   -> The values of the enum are grouped second by this element

        // - The third element can be any of the remaining 2 options
        //   -> We normalise this to {0, 1} by subtracting the first and/or second
        //      element, whichever is smaller than the third
        //   -> The values of the enum are grouped third by this element

        if (i1 < i2 && i0 < i2) { // Normalise third element
            i2 -= 2;
        } else if (i0 < i2 || i1 < i2) {
            i2--;
        }

        if (i1 > i0) { // Normalise second element
            i1--;
        }

        return VALUES[i0 * 6 + i1 * 2 + i2]; // Look up value
    }
}
