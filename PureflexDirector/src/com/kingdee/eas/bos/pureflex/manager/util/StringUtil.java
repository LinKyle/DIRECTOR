/********************************************
 *           EAS Admin Console
 *
 *           Author: Rock You 
 *           Copyright (c) 2005
 *     Kingdee Software (China) Co., Ltd.     
 ********************************************
 */
package com.kingdee.eas.bos.pureflex.manager.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * 
 */
public abstract class StringUtil {
    protected static final SecureRandom secureRandom             = new SecureRandom();
    private static final String         FOLDER_SEPARATOR         = "/";               // folder
    // separator

    private static final String         WINDOWS_FOLDER_SEPARATOR = "\\";              // Windows
    // folder
    // separator

    private static final String         TOP_PATH                 = "..";              // top
    // folder

    private static final String         CURRENT_PATH             = ".";               // current

    // folder

    /**
     * �滻source�е�����${paramName}, ����paramNameΪargs��key, �����׳�Exception
     * 
     * @param source
     * @param args
     * @return
     * @throws Exception
     */
    public static String replaceString(String source, Map args) throws Exception {
        int startIndex = 0;
        int openIndex = source.indexOf("${", startIndex);
        if (openIndex == -1) {
            return source;
        }

        int closeIndex = source.indexOf('}', startIndex);
        if ((closeIndex == -1) || (openIndex > closeIndex)) {
            return source;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(source.substring(startIndex, openIndex));
        while (true) {
            String key = source.substring(openIndex + 2, closeIndex);
            Object val = args.get(key);
            if (val != null) {
                sb.append(val);
            } else {
                throw new Exception("Property[" + key + "] not found!");
            }

            startIndex = closeIndex + 1;
            openIndex = source.indexOf("${", startIndex);
            if (openIndex == -1) {
                sb.append(source.substring(startIndex));
                break;
            }

            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                sb.append(source.substring(startIndex));
                break;
            }
            sb.append(source.substring(startIndex, openIndex));
        }
        return sb.toString();
    }

    public static String replaceMacro(String source, String macro, String value) {
        int startIndex = 0;
        int openIndex = source.indexOf("${", startIndex);
        if (openIndex == -1) {
            return source;
        }

        int closeIndex = source.indexOf('}', startIndex);
        if ((closeIndex == -1) || (openIndex > closeIndex)) {
            return source;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(source.substring(startIndex, openIndex));
        while (true) {
            String key = source.substring(openIndex + 2, closeIndex);
            if (key.equals(macro)) {
                sb.append(value);
            } else {
                sb.append("${").append(key).append("}");
            }
            startIndex = closeIndex + 1;
            openIndex = source.indexOf("${", startIndex);
            if (openIndex == -1) {
                sb.append(source.substring(startIndex));
                break;
            }

            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                sb.append(source.substring(startIndex));
                break;
            }
            sb.append(source.substring(startIndex, openIndex));
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------
    // General convenience methods for working with Strings
    // ---------------------------------------------------------------------

    /**
     * Check if a String has length.
     * <p>
     * 
     * <pre>
     *  StringUtils.hasLength(null) = false
     *  StringUtils.hasLength(&quot;&quot;) = false
     *  StringUtils.hasLength(&quot; &quot;) = true
     *  StringUtils.hasLength(&quot;Hello&quot;) = true
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is not null and has length
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check if a String has text. More specifically, returns <code>true</code>
     * if the string not <code>null<code>, it's <code>length is > 0</code>, and
     * it has at least one non-whitespace character.
     * <p><pre>
     *  StringUtils.hasText(null) = false
     *  StringUtils.hasText(&quot;&quot;) = false
     *  StringUtils.hasText(&quot; &quot;) = false
     *  StringUtils.hasText(&quot;12345&quot;) = true
     *  StringUtils.hasText(&quot; 12345 &quot;) = true
     * </pre>
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not null, length > 0,
     *         and not whitespace only
     * @see java.lang.Character#isWhitespace
     */
    public static boolean hasText(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trim leading whitespace from the given String.
     * 
     * @param str
     *            the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    /**
     * Trim trailing whitespace from the given String.
     * 
     * @param str
     *            the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimTrailingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * Test if the given String starts with the specified prefix, ignoring
     * upper/lower case.
     * 
     * @param str
     *            the String to check
     * @param prefix
     *            the prefix to look for
     * @see java.lang.String#startsWith
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * Count the occurrences of the substring in string s.
     * 
     * @param str
     *            string to search in. Return 0 if this is null.
     * @param sub
     *            string to search for. Return 0 if this is null.
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
            return 0;
        }
        int count = 0, pos = 0, idx = 0;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * Replace all occurences of a substring within a string with another
     * string.
     * 
     * @param inString
     *            String to examine
     * @param oldPattern
     *            String to replace
     * @param newPattern
     *            String to insert
     * @return a String with the replacements
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        }
        if (oldPattern == null || newPattern == null) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();
        // output StringBuffer we'll build up
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return sbuf.toString();
    }

    /**
     * Delete all occurrences of the given substring.
     * 
     * @param pattern
     *            the pattern to delete all occurrences of
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * Delete any character in a given string.
     * 
     * @param charsToDelete
     *            a set of characters to delete. E.g. "az\n" will delete 'a's,
     *            'z's and new lines.
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (inString == null || charsToDelete == null) {
            return inString;
        }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                out.append(c);
            }
        }
        return out.toString();
    }

    // ---------------------------------------------------------------------
    // Convenience methods for working with formatted Strings
    // ---------------------------------------------------------------------

    /**
     * Unqualify a string qualified by a '.' dot character. For example,
     * "this.name.is.qualified", returns "qualified".
     * 
     * @param qualifiedName
     *            the qualified name
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * Unqualify a string qualified by a separator character. For example,
     * "this:name:is:qualified" returns "qualified" if using a ':' separator.
     * 
     * @param qualifiedName
     *            the qualified name
     * @param separator
     *            the separator
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * Capitalize a <code>String</code>, changing the first letter to upper
     * case as per {@link Character#toUpperCase(char)}. No other letters are
     * changed.
     * 
     * @param str
     *            the String to capitalize, may be null
     * @return the capitalized String, <code>null</code> if null
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * Uncapitalize a <code>String</code>, changing the first letter to lower
     * case as per {@link Character#toLowerCase(char)}. No other letters are
     * changed.
     * 
     * @param str
     *            the String to uncapitalize, may be null
     * @return the uncapitalized String, <code>null</code> if null
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    /**
     * Extract the filename from the given path, e.g. "mypath/myfile.txt" ->
     * "myfile.txt".
     * 
     * @param path
     *            the file path
     * @return the extracted filename
     */
    public static String getFilename(String path) {
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    /**
     * Apply the given relative path to the given path, assuming standard Java
     * folder separation (i.e. "/" separators);
     * 
     * @param path
     *            the path to start from (usually a full file path)
     * @param relativePath
     *            the relative path to apply (relative to the full file path
     *            above)
     * @return the full file path that results from applying the relative path
     */
    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith("/")) {
                newPath += "/";
            }
            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

    /**
     * Normalize the path by suppressing sequences like "path/.." and inner
     * simple dots folders.
     * <p>
     * The result is convenient for path comparison. For other uses, notice that
     * Windows separators ("\") are replaced by simple dashes.
     * 
     * @param path
     *            the original path
     * @return the normalized path
     */
    public static String cleanPath(String path) {
        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
        List pathElements = new LinkedList();
        int tops = 0;
        for (int i = pathArray.length - 1; i >= 0; i--) {
            if (CURRENT_PATH.equals(pathArray[i])) {
                // do nothing
            } else if (TOP_PATH.equals(pathArray[i])) {
                tops++;
            } else {
                if (tops > 0) {
                    tops--;
                } else {
                    pathElements.add(0, pathArray[i]);
                }
            }
        }
        return collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
    }

    /**
     * Compare two paths after normalization of them.
     * 
     * @param path1
     *            First path for comparizon
     * @param path2
     *            Second path for comparizon
     * @return True if the two paths are equivalent after normalization
     */
    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }


    // ---------------------------------------------------------------------
    // Convenience methods for working with String arrays
    // ---------------------------------------------------------------------

    /**
     * Append the given String to the given String array, returning a new array
     * consisting of the input array contents plus the given String.
     * 
     * @param arr
     *            the array to append to
     * @param str
     *            the String to append
     * @return the new array
     */
    public static String[] addStringToArray(String[] arr, String str) {
        String[] newArr = new String[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[arr.length] = str;
        return newArr;
    }
    
    public static String[] addStringArrToArray(String[] arr, String[] strs) {
    	String[] resultArr = arr;
        if(strs!=null && strs.length>0) {
        	for(int i=0; i<strs.length; i++) {
        		resultArr = addStringToArray(resultArr, strs[i]);
        	}
        }
        return resultArr;
    }






    /**
     * Take a String which is a delimited list and convert it to a String array.
     * <p>
     * A single delimiter can consists of more than one character: It will still
     * be considered as single delimiter string, rather than as bunch of
     * potential delimiter characters - in contrast to
     * <code>tokenizeToStringArray</code>.
     * 
     * @param str
     *            the input String
     * @param delimiter
     *            the delimiter between elements (this is a single delimiter,
     *            rather than a bunch individual delimiter characters)
     * @return an array of the tokens in the list
     * @see #tokenizeToStringArray
     */
    public static String[] delimitedListToStringArray(String str, String delimiter) {
        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[] { str };
        }

        List result = new ArrayList();
        int pos = 0;
        int delPos = 0;
        while ((delPos = str.indexOf(delimiter, pos)) != -1) {
            result.add(str.substring(pos, delPos));
            pos = delPos + delimiter.length();
        }
        if (str.length() > 0 && pos <= str.length()) {
            // Add rest of String, but not in case of empty input.
            result.add(str.substring(pos));
        }

        return (String[]) result.toArray(new String[result.size()]);
    }

    /**
     * Convert a CSV list into an array of Strings.
     * 
     * @param str
     *            CSV list
     * @return an array of Strings, or the empty array if s is null
     */
    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    /**
     * Convenience method to convert a CSV string list to a set. Note that this
     * will suppress duplicates.
     * 
     * @param str
     *            CSV String
     * @return a Set of String entries in the list
     */
    public static Set commaDelimitedListToSet(String str) {
        Set set = new TreeSet();
        String[] tokens = commaDelimitedListToStringArray(str);
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }
        return set;
    }

    /**
     * Convenience method to return a String array as a delimited (e.g. CSV)
     * String. E.g. useful for toString() implementations.
     * 
     * @param arr
     *            array to display. Elements may be of any type (toString will
     *            be called on each element).
     * @param delim
     *            delimiter to use (probably a ",")
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (arr == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for toString() implementations.
     * 
     * @param coll
     *            Collection to display
     * @param delim
     *            delimiter to use (probably a ",")
     * @param prefix
     *            string to start each element with
     * @param suffix
     *            string to end each element with
     */
    public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
        if (coll == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        Iterator it = coll.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(prefix).append(it.next()).append(suffix);
            i++;
        }
        return sb.toString();
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for toString() implementations.
     * 
     * @param coll
     *            Collection to display
     * @param delim
     *            delimiter to use (probably a ",")
     */
    public static String collectionToDelimitedString(Collection coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Convenience method to return a String array as a CSV String. E.g. useful
     * for toString() implementations.
     * 
     * @param arr
     *            array to display. Elements may be of any type (toString will
     *            be called on each element).
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    /**
     * Convenience method to return a Collection as a CSV String. E.g. useful
     * for toString() implementations.
     * 
     * @param coll
     *            Collection to display
     */
    public static String collectionToCommaDelimitedString(Collection coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String getRandomString(int len) {
        byte[] bytes = new byte[len];
        secureRandom.nextBytes(bytes);
        String randomString = new String(bytes);
        return randomString;

    }

    public static boolean isCharacterOrNum(String value) {
        return Pattern.matches("[a-zA-Z_0-9]*", value);
    }

    public static boolean isNum(String value) {
        if (value == null)
            return false;
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUTF8(String str)  {
        if (str == null)
            return true;
        for (int i = 0; i < str.length(); i++) {
            char aChar = str.charAt(i);
            if(Character.getType(aChar)==Character.FORMAT)
                continue;
            
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(aChar);
            if(ub.equals(Character.UnicodeBlock.BASIC_LATIN) || ub.equals(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
                continue;
            }else {
                return false;
            }
        }
        return true;
    }
    
}
