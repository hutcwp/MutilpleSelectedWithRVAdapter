# MutilpleSelectedWithRVAdapter
一个使用RecyclerView.Adapter实现的多选模式Demo

要求：自定义 RecyclerView.Adapter 实现列表的多选功能。

1.长按任意一个列表中的 item，进入多选模式，被长按的那个 item 呈被选中状态。此后点击任意一个其他未被选中的 item, 此 item 的状态变成选中状态，如果点击一个已被选中的 item，则此 item 的状态变为未被选中。
2.当所有的 item 都没有被选中时，则退出多选模式。
3.此 Adapter 需要提供一个清除所有已选状态的方法。
4.列表中 item 的状态可通过它的不同的背景色来表示。

UI界面图：

主界面：

  ![这里写图片描述](https://raw.githubusercontent.com/hutcwp/img-floder/master/%E5%A4%9A%E9%80%89%E6%A8%A1%E5%BC%8FDemo_%E4%B8%BB%E7%95%8C%E9%9D%A2.png)

普通模式：

  https://raw.githubusercontent.com/hutcwp/img-floder/master/%E5%A4%9A%E9%80%89%E6%A8%A1%E5%BC%8FDemo_%E6%99%AE%E9%80%9A%E6%A8%A1%E5%BC%8F.png

多选模式

  ![这里写图片描述](https://raw.githubusercontent.com/hutcwp/img-floder/master/%E5%A4%9A%E9%80%89%E6%A8%A1%E5%BC%8FDemo_%E5%A4%9A%E9%80%89%E6%A8%A1%E5%BC%8F.png)
  
  
  解释：
  1.进入app后，会显示主界面，此时处于普通模式并且单击事件无效，
  2.当长按某一个Item时，会进入多选模式，底部button变为可见，并有toast提示当前所处的模式，此时单击事件生效，点击可改变Item选中状态（灰色未选中，蓝色为选中）且长按的那个Item会成为选中状态。
  3.当所有的Item均未选中或者点击底部的清除所有选中状态Button时，会退出多选模式。 
