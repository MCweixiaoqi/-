# -
我的世界mod
# 拆解台 Mod (Disassembly Table)

一个可以拆解 Minecraft 中任何物品（包括其他 Mod 物品）的模组，能够返还合成材料和提取附魔效果到附魔书上。

## 功能特点

- **支持任何 Mod 物品**：只要物品有合成配方，无论来自哪个 Mod，都可以被拆解
- **耐久度检测**：
  - 耐久度低于三分之二（但高于三分之一）的物品：返还的材料会少一个
  - 耐久度低于三分之一的物品：返还的材料会少两个
- **附魔提取**：可以从附魔物品中提取附魔效果，生成对应的附魔书
- **即时拆解**：放入物品后立即拆解，无需等待
- **双平台支持**：同时支持 Forge 和 Fabric 加载器
- **无依赖**：不需要任何前置 mod 或其他依赖

## 合成配方

拆解台的合成配方如下：

        I D I  
	    W C W  
	    W W W 

其中：
- I = 铁锭
- D = 钻石
- C = 工作台
- W = 任意木板（支持所有类型的木板）

## 构建说明

### Windows

1. 下载或克隆这个仓库
2. 运行 `build_mod.bat` 脚本
3. 构建完成后，你可以在以下位置找到生成的 .jar 文件：
   - Forge 版本：`forge/build/libs/disassemblytable-1.0.0.jar`
   - Fabric 版本：`fabric/build/libs/disassemblytable-1.0.0.jar`

### Linux/Mac

1. 下载或克隆这个仓库
2. 运行 `chmod +x build_mod.sh` 使脚本可执行
3. 运行 `./build_mod.sh` 脚本
4. 构建完成后，你可以在以下位置找到生成的 .jar 文件：
   - Forge 版本：`forge/build/libs/disassemblytable-1.0.0.jar`
   - Fabric 版本：`fabric/build/libs/disassemblytable-1.0.0.jar`

## 安装方法

1. 确保你已安装 Minecraft 1.20.1 和对应的 Forge 或 Fabric 加载器
2. 将生成的 .jar 文件复制到你的 Minecraft 实例的 `mods` 文件夹中
3. 启动游戏

## 使用方法

1. 在游戏中合成拆解台
2. 放置拆解台并右键点击打开界面
3. 将你想要拆解的物品放入输入槽
4. 物品会立即被拆解，根据耐久度返还相应数量的材料和附魔书（如果有）
5. 从输出槽中取出材料和附魔书

## 提示

- 物品的耐久度越高，返还的材料就越多
- 在拆解贵重物品前，可以先修复它们以获得更多材料
- 附魔书的提取不受耐久度影响，所有附魔都会被完整提取

## 许可证

MIT License
