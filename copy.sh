#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <section_number> [<next_section_number>]"
  exit 1
fi

SRC_NUM=$(printf "%02d" "$1")

if [ -z "$2" ]; then
  DST_NUM=$(printf "%02d" $((10#$1 + 1)))
else
  DST_NUM=$(printf "%02d" "$2")
fi

SRC_DIR="section_${SRC_NUM}"
DST_DIR="section_${DST_NUM}"
SRC_SUB="springsecsection$((10#$SRC_NUM))"
DST_SUB="springsecsection$((10#$DST_NUM))"

if [ ! -d "$SRC_DIR" ]; then
  echo "Source directory $SRC_DIR does not exist."
  exit 1
fi

cp -r "$SRC_DIR" "$DST_DIR"

mv "$DST_DIR/$SRC_SUB" "$DST_DIR/$DST_SUB"

REMOVE_DIRS=(".idea" "target")

for dir in "${REMOVE_DIRS[@]}"; do
  find "$DST_DIR" -type d -name "$dir" -exec rm -rf {} +
done

grep -rl "$SRC_SUB" "$DST_DIR" | xargs sed -i "s/$SRC_SUB/$DST_SUB/g"

echo "✅ $SRC_DIR successfully copied to $DST_DIR"
echo "🔁 Subdirectory renamed from $SRC_SUB to $DST_SUB"
echo "🧹 Unnecessary directories removed: ${REMOVE_DIRS[*]}"
echo "🔄 Internal references updated from $SRC_SUB to $DST_SUB"
