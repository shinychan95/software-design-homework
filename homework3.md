
# Problem 1


Let $`\mathcal{N}`$ be the set of all elements of type $`\textsf{N}`$, and $`\mathsf{null} \notin \mathcal{N}`$ be an distinguished element to represent `null`. Write formal abstract specifications of the interfaces below with respect to following abstract values:

- A graph is a pair $`G = (V, E)`$, where $`V \subseteq \mathcal{N}`$ and $`E \subseteq V \times V`$.
- A tree is a triple $`T = (V, E, \hat{v})`$, where $`(V, E)`$ is a graph and $`\hat{v} \in \mathcal{N}`$ denotes the root.

Other data types, such as `boolean`, `int`, `Set<N>`, etc. have conventional abstract values, e.g., Boolean values, integers, and subsets of $`\mathcal{N}`$, etc.

## `Graph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### containsVertex

```java 
boolean containsVertex(N vertex);
```

- requires: 
	+ vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns true if vertex is in $`V_{this}`$; and
    - returns false, otherwise.

##### containsEdge

```java
boolean containsEdge(N source, N target);
```

- requires: <!-- TODO --> 
	+ source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$, target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->  
    + returns true if edge, which contains given source and target, is in $`E_{this}`$; and
    - returns false, otherwise.
##### getSources

```java
Set<N> getSources(N target);
```

- requires: <!-- TODO --> 
	+ target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO --> 
	+ returns Set(N) containing all the sources, which each of them is inner variable of edge that has given target.

##### getTargets

```java
Set<N> getTargets(N source);
```

- requires: <!-- TODO --> 
	+ source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ returns Set(N) containing all the targets, which each of them is inner variable of edge that has given source.


## `Tree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

<!-- TODO -->

1. There is root vertex $`\hat{v}_{this}`$.
2. $`\hat{v}_{this}`$ has zero or more child vertices.
3. Its child vertex also has zero or more child nodes, which are defined repeatedly.
4. So, every vertices including $`\hat{v}_{this}`$ is in $`V_{this}`$. And every parent-child relationship describes as edge in $`E_{this}`$

##### getDepth

```java
int getDepth(N vertex);
```

- requires: 
  + vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$; and
  + getRoot.isPresent()
- ensures:  
  + returns 0 if vertex is getRoot.get(); and
  + returns getDepth(getParent(vertex)) + 1, otherwise.

##### getHeight

```java
int getHeight();
```

- requires: <!-- TODO --> 
	+ getRoot.isPresent()
- ensures:  <!-- TODO --> -   
	+ returns maximum value of depth that all vertex is in $`\mathcal{N}`$.

##### getRoot

```java
Optional<N> getRoot();
```

- requires: <!-- TODO --> 
	+ none
- ensures:  <!-- TODO -->
	+ returns $`\hat{v}_{this}`$ if exist; and
	+ returns otherwise, Optional.empty()

##### getParent

```java
Optional<N> getParent(N vertex);
```

- requires: <!-- TODO -->
	+ vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
	+ getRoot.isPresent()
- ensures:  <!-- TODO -->
	+ returns source that $`(source, vertex) \in E_{this}`$ if source exist; and
	+ return otherwise, Optional.empty()


## `MutableGraph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object,
and $`G_{next} = (V_{next}, E_{next})`$ be an abstract value of the graph object _modified by_ the method call. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: 
	+ vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} \cup \{ vertex \}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified)
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\{ vertex \} \notin V_{this}`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: <!-- TODO -->
	+ vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$ 
- ensures:  <!-- TODO -->
	+ $`V_{next} = V_{this} - \{ vertex \}`$
	+ $`E_{next} = E_{this} - \forall (vertex, v) - \forall (w, vertex)`$
	+ If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`\{ vertex \} \in V_{next}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: <!-- TODO -->
	+ source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
	+ target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ $`V_{next} = V_{this} \cup \{ source \} \cup \{ target \}`$
	+ $`E_{next} = E_{this} \cup (source, target)`$
	+ If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`(source, target) \notin E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: <!-- TODO -->
	+ source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
	+ target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ $`V_{next} = V_{this}`$
	+ $`E_{next} = E_{this} - (source, target)`$
	+ If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`(source, target) \notin E_{next}`$.


## `MutableTree<N>`

##### Class invariant 

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current tree object,
and $`T_{next} = (V_{next}, E_{next}, \hat{v}_{next})`$ be an abstract value of the tree object _modified by_ the method call. 

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: <!-- TODO -->
	+ vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ return false If $`V_{this} \ is \ not \ empty`$; and
	+ $`V_{next} = \{ vertex \}`$
	+ $`\hat{v}_{next} = \{ vertex \}`$
	+ $`E_{next} = E_{this}`$ (the edges are not modified)
	+ If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`V_{this} \ is \  empty`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: <!-- TODO -->
	+ vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ $`V_{next} = V_{this} - \{ vertex \} - `$ all vertices that are descendants of the given vertex
	+ $`E_{next} = E_{this} - `$ all edges that connect with parent of vertex, vertex and descendants of vertex
	+ If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`\{ vertex \} \in V_{this}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: <!-- TODO -->
	+ source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
	+ target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ return false If $`source \notin V_{this}`$ or  $`target \in V_{This}`$; and
	+ $`V_{next} = V_{this} \cup \{ target \}`$
	+ $`E_{next} = E_{this} \cup (source, target)`$
	+ If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`(source, target) \notin E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: <!-- TODO -->
	+ source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
	+ target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  <!-- TODO -->
	+ $`V_{next} = V_{this} - \{ target \} - `$ all vertices that are descendants of the given target
	+ $`E_{next} = E_{this} - `$ all edges that connect with parent of target, vertex and descendants of target
	+ If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
	+ returns true if and only if $`(source, target) \in E_{this}`$.

# Problem 2

Identify whether the abstract interfaces satisfy the Liskov substitution principle.
For each question, explain your reasoning _using the abstract specifications that you have defined in Problem 1_. 


##### `Tree<N>` and `Graph<N>`

* Is `Tree<N>` a subtype of `Graph<N>`?
<!-- TODO -->

##### `MutableGraph<N>` and `Graph<N>`

* Is `MutableGraph<N>` a subtype of `Graph<N>`
<!-- TODO -->

##### `MutableTree<N>` and `Tree<N>`

* Is `MutableTree<N>` a subtype of `Tree<N>`
<!-- TODO -->

##### `MutableTree<N>` and `MutableGraph<N>`

* Is `MutableTree<N>` a subtype of `MutableGraph<N>`
<!-- TODO -->