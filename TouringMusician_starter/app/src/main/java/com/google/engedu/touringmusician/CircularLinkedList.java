/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
    }

    Node head;

    public void insertBeginning(Point p) {
        Node n = new Node();
        n.point = p;

        // If size 0
        if (head == null) {
            n.next = n;
            n.prev = n;
        }
        else {
            n.prev = head.prev;
            head.prev.next = n;
            head.prev = n;
            n.next = head;
        }
        head = n;
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        CircularLinkedListIterator iterator = new CircularLinkedListIterator();
        Point current = iterator.next();
        Point first = current;
        while(iterator.hasNext()) {
            Point next = iterator.next();
            total += distanceBetween(current, next);
            current = next;
        }
        total += distanceBetween(current, first);
        return total;
    }

    public void insertNearest(Point p) {
        Node n = new Node();
        n.point = p;

        if (head == null) {
            n.next = n;
            n.prev = n;
            head = n;
        }
        else {
            CircularLinkedListIterator iterator = new CircularLinkedListIterator();
            float min = Float.MAX_VALUE;
            Node temp = head, curr = head;
            do {
                Point current = iterator.next();
                float dist = distanceBetween(current, p);
                // Find the min
                if (dist < min) {
                    min = dist;
                    temp = curr;
                }
                curr = curr.next;
            } while(iterator.hasNext());
            // Reorder nodes
            temp.next.prev = n;
            n.prev = temp;
            n.next = temp.next;
            temp.next = n;
        }
    }

    public void insertSmallest(Point p) {
        Node n = new Node();
        n.point = p;

        // If size 0 or 1, just add to the beginning
        if (head == null || head.next == head)
            insertBeginning(p);
        else {
            CircularLinkedListIterator iterator = new CircularLinkedListIterator();
            float min = Float.MAX_VALUE;
            Node temp = head, curr = head;
            do {
                Point current = iterator.next();
                // Distance 1 to 2
                float dist = distanceBetween(current, curr.next.point);
                // Distance 1 to 3 to 2
                float newDist = distanceBetween(current, p) + distanceBetween(curr.next.point, p);
                // Check how big the increase is
                if (newDist - dist < min) {
                    min = newDist - dist;
                    temp = curr;
                }
                curr = curr.next;
            } while(iterator.hasNext());
            // Reorder the nodes
            temp.next.prev = n;
            n.prev = temp;
            n.next = temp.next;
            temp.next = n;
        }

    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
