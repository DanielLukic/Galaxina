/*
 * J2ME Fixed-Point Math Library
 *
 * Dan Carter, 2004
 * http://orbisstudios.com
 */

package net.intensicode.galaxina.util;

/**
 * <pre>
 * <b>J2ME Fixed-Point Math Library</b>
 * <p/>
 * Provided under the artistic license: <a href="http://www.opensource.org/licenses/artistic-license.html">http://www.opensource.org/licenses/artistic-license.html</a>
 * <p/>
 * Basically it means you can use this library for free, even for commercial purposes.
 * <p/>
 * <p/>
 * <b>References:</b>
 * <p/>
 * exp(), log(), atan2() converted from this free, floating-point implementation:
 * <a href="http://www.netlib.org/fdlibm/">http://www.netlib.org/fdlibm</a>
 * <p/>
 * sin(), asin() converted from the free, fast trigonometric library found at:
 * <a href="http://www.magic-software.com">http://www.magic-software.com</a>
 * <p/>
 * <p/>
 * <b>Author:</b> Dan Carter (<a href="http://orbisstudios.com">orbisstudios.com</a>)
 * </pre>
 */

public abstract class MathFP
    {

    public static int default_precision = 13;

    /**
     * number of fractional bits in all operations, do not modify directly
     */
    public static int precision = 0;

    public static int frac_mask = 0;

    private static final int maxPrecision = 30;

    private static final int e_precision = 29;

    private static final int e = 1459366444;        // 2.7182818284590452353602874713527 * 2^29

    private static final int pi_precision = 29;

    private static final int pi = 1686629713;       // 3.1415926535897932384626433832795

    private static int one_eighty_over_pi;

    private static int pi_over_one_eighty;

    private static int maxDigits;

    private static int maxDigitsInt;

    public static int ONE, HALF, TWO, E, PI, PI_HALF, PI_TWO;

    /**
     * largest possible number
     */
    public static final int INFINITY = 0x7fffffff;

    private static final int sk_precision = 31;

    private static final int sk[] = {
            16342350,       //7.61e-03 * 2^31
            356589659,      //1.6605e-01
    };

    private static int SK[] = new int[sk.length];

    private static final int as_precision = 30;

    private static final int as[] = {
            -20110432,      //-0.0187293 * 2^30
            79737141,       //0.0742610
            227756102,      //0.2121144
            1686557206      //1.5707288
    };

    private static int AS[] = new int[as.length];

    private static final int ln2_precision = 30;

    private static final int ln2 = 744261117;           //0.69314718055994530941723212145818 * 2^30

    private static final int ln2_inv = 1549082004;      //1.4426950408889634073599246810019

    private static int LN2, LN2_INV;

    private static final int lg_precision = 31;

    private static final int lg[] = {
            1431655765,     //6.666666666666735130e-01 * 2^31
            858993459,      //3.999999999940941908e-01
            613566760,      //2.857142874366239149e-01
            477218077,      //2.222219843214978396e-01
            390489238,      //1.818357216161805012e-01
            328862160,      //1.531383769920937332e-01
            317788895       //1.479819860511658591e-01
    };

    private static int LG[] = new int[lg.length];

    private static final int exp_p_precision = 31;

    private static final int exp_p[] = {
            357913941,      //1.66666666666666019037e-01 * 2^31
            -5965232,       //-2.77777777770155933842e-03
            142029,         //6.61375632143793436117e-05
            -3550,          //-1.65339022054652515390e-06
            88,             //4.13813679705723846039e-08
    };

    private static int EXP_P[] = new int[exp_p.length];

    static
        {
        setPrecision( default_precision );
        }

    /**
     * Converts an int to a FP.
     *
     * @param f int to convert
     * @return FP
     */
    public static int toFP( int f )
        {
        return ( f < 0 ) ? -( -f << precision ) : f << precision;
        }

    /**
     * Converts a FP to an int.
     *
     * @param f FP to convert
     * @return int
     */
    public static int toInt( int f )
        {
        return ( f < 0 ) ? -( -f >> precision ) : f >> precision;
        }

    /**
     * Converts a FP to the current set precision.
     *
     * @param x the FP to convert
     * @param p the precision of the FP passed in
     * @return a FP of the current precision
     */
    public static int convert( int x, int p )
        {
        int num, xabs = abs( x );
        if ( p > maxPrecision || p < 0 ) return x;
        if ( p > precision )
            num = xabs >> ( p - precision );
        else
            num = xabs << ( precision - p );
        if ( x < 0 )
            num = -num;
        return num;
        }

    /**
     * Converts a string to a FP.
     * <br>
     * <br>The string should trimmed of any whitespace before-hand.
     * <br>
     * <br>A few examples of valid strings:<br>
     * <pre>
     * .01
     * 0.01
     * 10
     * 130.0
     * -30000.12345
     * </pre>
     *
     * @param s the string to convert
     * @return FP
     */
    public static int toFP( String s )
        {
        int x, i, integer, frac = 0;
        String fracstring = null;
        boolean neg = false;
        if ( s.charAt( 0 ) == '-' )
            {
            neg = true;
            s = s.substring( 1, s.length() );
            }
        int index = s.indexOf( '.' );

        if ( index < 0 )
            {
            integer = Integer.parseInt( s );
            }
        else if ( index == 0 )
            {
            integer = 0;
            fracstring = s.substring( 1, s.length() );
            }
        else if ( index == s.length() - 1 )
            {
            integer = Integer.parseInt( s.substring( 0, index ) );
            }
        else
            {
            integer = Integer.parseInt( s.substring( 0, index ) );
            fracstring = s.substring( index + 1, s.length() );
            }

        if ( fracstring != null )
            {
            if ( fracstring.length() > maxDigits )
                fracstring = fracstring.substring( 0, maxDigits );
            if ( fracstring.length() > 0 )
                {
                frac = Integer.parseInt( fracstring );
                for ( i = 0; i < maxDigits - fracstring.length(); i++ )
                    frac *= 10;
                }
            }
        x = ( integer << precision ) + ( ( frac * ONE ) / maxDigitsInt );
        if ( neg )
            x = -x;
        return x;
        }

    /**
     * Sets the precision for all fixed-point operations.
     * <br>
     * <br>The maximum precision is 31 bits.
     *
     * @param p the desired precision in number of bits
     */
    public static void setPrecision( int p )
        {
        if ( p > maxPrecision || p < 0 ) return;
        int i;
        precision = p;
        PI = ( p <= pi_precision ) ? pi >> ( pi_precision - p ) : pi << ( p - pi_precision );
        PI_HALF = PI >> 1;
        PI_TWO = PI << 1;
        ONE = 1 << p;
        HALF = ONE >> 1;
        TWO = ONE << 1;
        E = ( p <= e_precision ) ? e >> ( e_precision - p ) : e >> ( p - e_precision );
        for ( i = 0; i < sk.length; i++ )
            SK[ i ] = ( p <= sk_precision ) ? sk[ i ] >> ( sk_precision - p ) : sk[ i ] << ( p - sk_precision );
        for ( i = 0; i < as.length; i++ )
            AS[ i ] = ( p <= as_precision ) ? as[ i ] >> ( as_precision - p ) : as[ i ] << ( p - as_precision );
        LN2 = ( p <= ln2_precision ) ? ln2 >> ( ln2_precision - p ) : ln2 << ( p - ln2_precision );
        LN2_INV = ( p <= ln2_precision ) ? ln2_inv >> ( ln2_precision - p ) : ln2_inv << ( p - ln2_precision );
        for ( i = 0; i < lg.length; i++ )
            LG[ i ] = ( p <= lg_precision ) ? lg[ i ] >> ( lg_precision - p ) : lg[ i ] << ( p - lg_precision );
        for ( i = 0; i < exp_p.length; i++ )
            EXP_P[ i ] = ( p <= exp_p_precision ) ? exp_p[ i ] >> ( exp_p_precision - p ) : exp_p[ i ] << ( p - exp_p_precision );
        frac_mask = ONE - 1;
        pi_over_one_eighty = div( PI, toFP( 180 ) );
        one_eighty_over_pi = div( toFP( 180 ), PI );

        int targetInt = ( 1 << maxPrecision - p );
        maxDigitsInt = 1;
        maxDigits = 0;
        while ( maxDigitsInt <= targetInt )
            {
            maxDigitsInt *= 10;
            maxDigits++;
            }
        maxDigitsInt /= 10;
        maxDigits--;
        }

    public static int abs( int x )
        {
        return ( x < 0 ) ? -x : x;
        }

    public static int ceil( int x )
        {
        boolean neg = false;
        if ( x < 0 )
            {
            x = -x;
            neg = true;
            }
        if ( ( x & frac_mask ) == 0 )
            return ( neg ) ? -x : x;
        if ( neg )
            return -( x & ~frac_mask );
        return ( x & ~frac_mask ) + ONE;
        }

    public static int floor( int x )
        {
        boolean neg = false;
        if ( x < 0 )
            {
            x = -x;
            neg = true;
            }
        if ( ( x & frac_mask ) == 0 )
            return ( neg ) ? -x : x;
        if ( neg )
            return -( x & ~frac_mask ) - ONE;
        return ( x & ~frac_mask );
        }

    /**
     * Removes the fractional part of a FP
     *
     * @param x the FP to truncate
     * @return a truncated FP
     */

    public static int trunc( int x )
        {
        return ( x < 0 ) ? -( -x & ~frac_mask ) : x & ~frac_mask;
        }

    /**
     * Returns the fractional part of a FP
     *
     * @param x FP to get fractional part of
     * @return positive fractional FP if input is positive, negative fractional otherwise
     */

    public static int frac( int x )
        {
        return ( x < 0 ) ? -( -x & frac_mask ) : x & frac_mask;
        }

    /**
     * Rounds a FP to the nearest whole number.
     *
     * @param x the FP to round
     * @return a rounded FP
     */
    public static int round( int x )
        {
        boolean neg = false;
        if ( x < 0 )
            {
            x = -x;
            neg = true;
            }
        x += HALF;
        x &= ~frac_mask;
        return ( neg ) ? -x : x;
        }

    /**
     * Multiplies two FPs.
     */
    public static int mul( int x, int y )
        {
        if ( x == 0 || y == 0 ) return 0;
        int xneg = 0, yneg = 0;
        if ( x < 0 )
            {
            xneg = 1;
            x = -x;
            }
        if ( y < 0 )
            {
            yneg = 1;
            y = -y;
            }
        int res = ( ( x >> precision ) * ( y >> precision ) << precision )
                + ( x & frac_mask ) * ( y >> precision )
                + ( x >> precision ) * ( y & frac_mask )
                + ( ( x & frac_mask ) * ( y & frac_mask ) >> precision );
        if ( ( xneg ^ yneg ) == 1 )
            res = -res;
        return res;
        }

    /**
     * Divides two FPs.
     */
    public static int div( int x, int y )
        {
        if ( x == 0 ) return 0;
        if ( y == 0 ) return ( x < 0 ) ? -INFINITY : INFINITY;
        int xneg = 0, yneg = 0;
        if ( x < 0 )
            {
            xneg = 1;
            x = -x;
            }
        if ( y < 0 )
            {
            yneg = 1;
            y = -y;
            }
        int msb = 0, lsb = 0;
        while ( ( x & ( 1 << maxPrecision - msb ) ) == 0 )
            msb++;
        while ( ( y & ( 1 << lsb ) ) == 0 )
            lsb++;
        int shifty = precision - ( msb + lsb );
        int res = ( ( x << msb ) / ( y >> lsb ) );
        if ( shifty > 0 )
            res <<= shifty;
        else
            res >>= -shifty;
        if ( ( xneg ^ yneg ) == 1 )
            res = -res;
        return res;
        }

    public static int sqrt( int x )
        {
        int s = ( x + ONE ) >> 1;
        for ( int i = 0; i < 8; i++ )
            {
            s = ( s + div( x, s ) ) >> 1;
            }
        return s;
        }

    /**
     * FP angle must be in radians.
     *
     * @param f the angle in radians
     * @return sin() of the angle
     */
    public static int sin( int f )
        {
        int sign = 1;
        f %= PI * 2;
        if ( f < 0 )
            f = PI * 2 + f;
        if ( ( f > PI_HALF ) && ( f <= PI ) )
            {
            f = PI - f;
            }
        else if ( ( f > PI ) && ( f <= ( PI + PI_HALF ) ) )
            {
            f = f - PI;
            sign = -1;
            }
        else if ( f > ( PI + PI_HALF ) )
            {
            f = ( PI << 1 ) - f;
            sign = -1;
            }

        int sqr = mul( f, f );
        int result = SK[ 0 ];
        result = mul( result, sqr );
        result -= SK[ 1 ];
        result = mul( result, sqr );
        result += ONE;
        result = mul( result, f );
        return sign * result;
        }

    /**
     * FP angle must be in radians.
     *
     * @param f the angle in radians
     * @return cos() of the angle
     */
    public static int cos( int f )
        {
        return sin( PI_HALF - f );
        }

    /**
     * FP angle must be in radians.
     *
     * @param f the angle in radians
     * @return tan() of the angle
     */
    public static int tan( int f )
        {
        return div( sin( f ), cos( f ) );
        }


    /**
     * input range: [-1, 1] -- output range: [-PI/2, PI/2]
     */
    public static int asin( int f )
        {
        boolean neg = false;
        if ( f < 0 )
            {
            neg = true;
            f = -f;
            }

        int fRoot = sqrt( ONE - f );
        int result = AS[ 0 ];

        result = mul( result, f );
        result += AS[ 1 ];
        result = mul( result, f );
        result -= AS[ 2 ];
        result = mul( result, f );
        result += AS[ 3 ];
        result = PI_HALF - ( mul( fRoot, result ) );
        if ( neg )
            result = -result;

        return result;
        }

    /**
     * input range: [-1, 1] -- output range: [0, PI]
     */
    public static int acos( int f )
        {
        return PI_HALF - asin( f );
        }

    /**
     * input range: [-INF, INF] -- output range: [-PI/2, PI/2]
     */
    public static int atan( int f )
        {
        return asin( div( f, sqrt( ONE + mul( f, f ) ) ) );
        }

    /**
     * input range: [-INF, INF] -- output range: [-PI, PI] -- Converts (x,y) in Cartesian to (r, <i>theta</i>) in polar, and returns <i>theta</i>.
     *
     * @param y
     * @param x
     * @return
     */

    //This is a finely tuned error around 0.  The inaccuracies stabilize at around this value.
    private static int atan2_zero_error = 65;

    public static int atan2( int y, int x )
        {
        if ( y == 0 )
            {
            if ( x >= 0 )
                {
                return 0;
                }
            else if ( x < 0 )
                {
                return PI;
                }
            }
        else if ( x >= -atan2_zero_error && x <= atan2_zero_error )
            {
            return ( y > 0 ) ? PI_HALF : -PI_HALF;
            }
        int z = atan( abs( div( y, x ) ) );
        if ( x > 0 )
            {
            return ( y > 0 ) ? z : -z;
            }
        else
            {
            return ( y > 0 ) ? PI - z : z - PI;
            }
        }

    /**
     * E raised to the FP x (e**x)
     */
    public static int exp( int x )
        {
        if ( x == 0 ) return ONE;
        int xabs = abs( x );
        int k = mul( xabs, LN2_INV );
        k += HALF;
        k &= ~frac_mask;
        if ( x < 0 )
            k = -k;
        x -= mul( k, LN2 );
        int z = mul( x, x );
        int R = TWO + mul( z, EXP_P[ 0 ] + mul( z, EXP_P[ 1 ] + mul( z, EXP_P[ 2 ] + mul( z, EXP_P[ 3 ] + mul( z, EXP_P[ 4 ] ) ) ) ) );
        int xp = ONE + div( mul( TWO, x ), R - x );
        if ( k < 0 )
            k = ONE >> ( -k >> precision );
        else
            k = ONE << ( k >> precision );
        return mul( k, xp );
        }

    /**
     * Natural logarithm ln(x)
     */
    public static int log( int x )
        {
        if ( x < 0 ) return 0;
        if ( x == 0 ) return -INFINITY;
        int log2 = 0, xi = x;
        while ( xi >= TWO )
            {
            xi >>= 1;
            log2++;
            }
        int f = xi - ONE;
        int s = div( f, TWO + f );
        int z = mul( s, s );
        int w = mul( z, z );
        int R = mul( w, LG[ 1 ] + mul( w, LG[ 3 ] + mul( w, LG[ 5 ] ) ) ) + mul( z, LG[ 0 ] + mul( w, LG[ 2 ] + mul( w, LG[ 4 ] + mul( w, LG[ 6 ] ) ) ) );
        return mul( LN2, ( log2 << precision ) ) + f - mul( s, f - R );
        }

    /**
     * Logarithm with specified FP base
     */
    public static int log( int x, int base )
        {
        return div( log( x ), log( base ) );
        }

    /**
     * FP x raised to FP y.
     */
    public static int pow( int x, int y )
        {
        if ( y == 0 ) return ONE;
        if ( x < 0 ) return 0;
        return exp( mul( log( x ), y ) );
        }

    public static int min( int x, int y )
        {
        return ( x < y ) ? x : y;
        }

    public static int max( int x, int y )
        {
        return ( x > y ) ? x : y;
        }

    /**
     * Converts degrees to radians.
     *
     * @param x FP in degrees
     * @return FP in radians
     */
    public static int deg2rad( int x )
        {
        return mul( x, pi_over_one_eighty );
        }

    /**
     * Converts radians to degrees.
     *
     * @param x FP in radians
     * @return FP in degrees
     */
    public static int rad2deg( int x )
        {
        return mul( x, one_eighty_over_pi );
        }
    }
