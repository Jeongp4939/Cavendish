import { createComment, getCommentsList } from "api/comments";
import { useState } from "react";
export default function CommentCreateComponent({
  boardId,
}) {
  const [comment, setComment] = useState("");
  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };
  const handleCommentSubmit = () => {
    if (comment === "") return;

    createComment(
      { boardId: boardId, contents: comment },
      () => {},
      () => {},
    );
    setComment("");
  };

  return (
    <div className="comment_area">
      <textarea
        className="comment_input"
        value={comment}
        onChange={handleCommentChange}
      />
      <button
        className="comment_input_button"
        onClick={handleCommentSubmit}
        type="button"
      >
        등록
      </button>
    </div>
  );
}
