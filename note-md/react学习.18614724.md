> React 应用程序是由 **组件** 组成的。一个组件是 UI（用户界面）的一部分，它拥有自己的逻辑和外观。组件可以小到一个按钮，也可以大到整个页面。

# React

## JSX

> **JSX** 是 JavaScript 语法扩展，可以让你在 JavaScript 文件中书写类似 HTML 的标签。虽然还有其它方式可以编写组件，但大部分 React 开发者更喜欢 JSX 的简洁性，并且在大部分代码库中使用它。

### JSX规则

#### 只能返回一个根元素

如果想要在一个组件中包含多个元素，**需要用一个父标签把它们包裹起来**。例如：采用一个 `<div>`标签

```react
export default function Demo() {
	return (
        <div>
          <h1>海蒂·拉玛的待办事项</h1>
          <img 
            src="https://i.imgur.com/yXOvdOSs.jpg" 
            alt="Hedy Lamarr" 
            class="photo"/>
		</div>
	)
}

```

如果你不想在标签中增加一个额外的 `<div>`根元素，可以采用react的 `Fragment`或 `<></>`将元素包裹起来。此时，渲染后的html不会有额外的 `div`标签

1. Fragment

```react
import { Fragment } from 'react';
export default function Demo() {
	return (
        <Fragment>
          <h1>海蒂·拉玛的待办事项</h1>
          <img 
            src="https://i.imgur.com/yXOvdOSs.jpg" 
            alt="Hedy Lamarr" 
            class="photo"/>
		</Fragment>
	)
}
```

2. `<></>`

```react
export default function Demo() {
	return (
        <>
          <h1>海蒂·拉玛的待办事项</h1>
          <img 
            src="https://i.imgur.com/yXOvdOSs.jpg" 
            alt="Hedy Lamarr" 
            class="photo"/>
		</>
	)
}
```

##### 为什么多个 JSX 标签需要被一个父元素包裹？

> JSX 虽然看起来很像 HTML，但在底层其实被转化为了 JavaScript 对象，你不能在一个函数中返回多个对象，除非用一个数组把他们包装起来。这就是为什么多个 JSX 标签必须要用一个父元素或者 Fragment 来包裹。

#### 标签必须闭合

JSX 要求标签必须正确闭合。像 `<img>` 这样的自闭合标签必须书写成 `<img />`，而像 `<li>oranges` 这样只有开始标签的元素必须带有闭合标签，需要改为 `<li>oranges</li>`。

#### 使用驼峰式命名法给大部分属性命名

#### 组件函数名必须使用大写

注意 `profile`的大小写。示例：

- 错误写法

```react
function profile() {
  return (
    <img
      src="https://i.imgur.com/QIrZWGIs.jpg"
      alt="Alan L. Hart"
    />
  );
}

export default function Gallery() {
  return (
    <section>
      <h1>了不起的科学家</h1>
      <profile />
      <profile />
      <profile />
    </section>
  );
}
```

- 正确写法

```react
function Profile() {
  return (
    <img
      src="https://i.imgur.com/QIrZWGIs.jpg"
      alt="Alan L. Hart"
    />
  );
}

export default function Gallery() {
  return (
    <section>
      <h1>了不起的科学家</h1>
      <Profile />
      <Profile />
      <Profile />
    </section>
  );
}
```

#### 尽量避免组件的嵌套

组件间的嵌套可能会导致组件结构臃肿，在渲染时，增加不必要的渲染，导致性能问题。如果要在组件间进行共享变量，可使用 `props`来进行组件间的调用

- 不规范写法

```react
export default function Gallery() {
    function Profile() {
      return (
        <img
          src="https://i.imgur.com/QIrZWGIs.jpg"
          alt="Alan L. Hart"
        />
      );
    }
  return (
    <section>
      <h1>了不起的科学家</h1>
      <Profile />
      <Profile />
      <Profile />
    </section>
  );
}
```

- 推荐写法

```react
function Profile() {
  return (
    <img
      src="https://i.imgur.com/QIrZWGIs.jpg"
      alt="Alan L. Hart"
    />
  );
}

export default function Gallery() {
  return (
    <section>
      <h1>了不起的科学家</h1>
      <Profile />
      <Profile />
      <Profile />
    </section>
  );
}
```

## React钩子

### userDemo

`useMemo` 是一个 React Hook，它在每次重新渲染的时候能够缓存计算的结果。

> * [跳过代价昂贵的重新计算](https://zh-hans.react.dev/reference/react/useMemo#skipping-expensive-recalculations)
> * [跳过组件的重新渲染](https://zh-hans.react.dev/reference/react/useMemo#skipping-re-rendering-of-components)
> * [防止过于频繁地触发 Effect](https://zh-hans.react.dev/reference/react/useMemo#preventing-an-effect-from-firing-too-often)
> * [记忆另一个 Hook 的依赖](https://zh-hans.react.dev/reference/react/useMemo#memoizing-a-dependency-of-another-hook)
> * [记忆一个函数](https://zh-hans.react.dev/reference/react/useMemo#memoizing-a-function)

```js
const cachedValue = useMemo(calculateValue, dependencies)
```

#### 参数

- calculateValue：要缓存的函数。它应该是一个没有任何参数的纯函数，并且可以返回任意类型。可以是一个react组件
- dependencies：要监控的值，如果值没有变化，则使用缓存的值作为返回值，否则更新缓存中的值。

#### 用法

```react

```

## React.Fragment 和 <></>

> `<></>` 其实是 React.Fragment 的语法糖，它们两个都可以用来对元素进行分组，渲染成 HTML 后不会增加额外的标签

> jsx语法编写必须要有一个根元素，类似于vue中的template的写法，再进行嵌套时，可能会出现额外增加dom节点，这样的弊端
>
> 1. 不符合html的dom规范
> 2. 增加一些额外无意义的标签，增加html渲染的内容，影响性能

### 问题示例

#### 不使用ragment

```react
import { Fragment } from 'react';

function TableData () {
  return  (
    <div>
      <td>Eat</td>
      <td>Learn</td>
      <td>Code</td>
    </div>
  );
}

export default function Table () {
  return (
    <table>
      <tbody>
        <tr>
        <TableData />
        </tr>
      </tbody>
    </table>
  );
}
```

上面的代码会渲染为：

> 渲染的结果就是在tr中增加了一个无意义的div节点。那 `TableData`方法可以把div去掉吗？显然是不行的。这是因为 jsx语法本身需要包含一个根元素，移除后，则不符合jsx的语法，编译会报错，无法渲染。

```html
<table>
  <tbody>
    <tr>
      <div>
        <td>Eat</td>
        <td>Learn</td>
        <td>code</td>
      </div>
    </tr>
  </tbody>
</table>

```

#### 使用 `React.Fragment`语法

```react
import { Fragment } from 'react';

function TableData () {
  return  (
    <Fragment>
      <td>Eat</td>
      <td>Learn</td>
      <td>Code</td>
    </Fragment>
  );
}

export default function Table () {
  return (
    <table>
      <tbody>
        <tr>
        <TableData />
        </tr>
      </tbody>
    </table>
  );
}

```

上面的代码会渲染为：

```html
<table>
  <tbody>
    <tr>
      <td>Eat</td>
      <td>Learn</td>
      <td>code</td>
    </tr>
  </tbody>
</table>
```

### 使用语法糖 `<></>`

```react
import { Fragment } from 'react';

function TableData () {
  return  (
    <>
      <td>Eat</td>
      <td>Learn</td>
      <td>Code</td>
    </>
  );
}

export default function Table () {
  return (
    <table>
      <tbody>
        <tr>
        <TableData />
        </tr>
      </tbody>
    </table>
  );
}
```

上述代码渲染为：

```html
<table>
  <tbody>
    <tr>
      <td>Eat</td>
      <td>Learn</td>
      <td>code</td>
    </tr>
  </tbody>
</table>
```

# React-Router

## useSearchParams

> - `useSearchParams` 钩子用于读取和修改当前位置 URL 中的查询字符串。可用来动态监听变量变化后，改变URL的参数

### 用法

```react
  const [searchParams, setSearchParams] = useSearchParams({
    size: String(pageSize),
  });
```

- 以地址：`http://localhost:3004/tasks?size=1&page=2`为例，可通过
